package org.neuroml1.model.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigInteger;
import java.util.List;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.neuroml1.model.Level3Cell;
import org.neuroml1.model.NeuroMLLevel3;
import org.neuroml1.model.meta.Point;
import org.neuroml1.model.morph.Cable;
import org.neuroml1.model.morph.Cell;
import org.neuroml1.model.morph.Morphology;
import org.neuroml1.model.morph.ObjectFactory;
import org.neuroml1.model.morph.Segment;
import static org.neuroml1.model.test.ChannelTest.getTempDir;
import org.neuroml1.model.util.NeuroML1ValidatorTest;
import org.neuroml1.model.util.NeuroMLConverter;

public class MorphTest
{
	@Test public void testSimpleMorphMarshalling() throws Exception
	{
		ObjectFactory ofac = new ObjectFactory();
		
		Morphology morph = ofac.createMorphology();
				
		Cell c = ofac.createCell();
		c.setName("JAXB-Generated-Simple-Cell");		
		
		Segment seg = ofac.createSegment();
		seg.setId(new BigInteger("1"));
		seg.setParent(new BigInteger("0"));
        
        Point prox = new Point();
        prox.setX(0);
        prox.setY(0);
        prox.setZ(0);
        prox.setDiameter(3.0);
        seg.setProximal(prox);
        
        Point dist = new Point();
        dist.setX(0);
        dist.setY(20);
        dist.setZ(0);
        dist.setDiameter(3.0);
        seg.setDistal(dist);
		
		List<Segment> seglist = c.getSegmentList();
		seglist.add(seg);
		
		List<Cell> clist = morph.getCellList();
		clist.add(c);

		
		NeuroMLConverter conv = new NeuroMLConverter();
        
        String tempFile = ChannelTest.getTempDir() + File.separator + "morphology-as-morphml.xml";
		conv.morphologyToXml(morph, tempFile);
        File f = new File(tempFile);

        System.out.println("Saved to: "+ f);
        assertTrue(f.exists());
        NeuroML1ValidatorTest.testValidate(f);
        


	}
	
	@Test public void testSimpleMorphUnmarshalling() throws Exception
	{
		NeuroMLConverter conv = new NeuroMLConverter();
						
		String wdir = System.getProperty("user.dir");
		String tdir = wdir + File.separator + "src/test/resources";
		String fpath = tdir + File.separator +"Simple.morph.xml";

        System.out.println("Performing test on "+fpath);
		
		Morphology morph = conv.xmlToMorphology(fpath);
		
		String notes = morph.getNotes();
		assertTrue("A single simple cell, using the MorphML namespace for validation.".equals(notes.trim()));
		
		List<Cell> clist = morph.getCellList();		
		Cell cell = clist.get(0);
		assertTrue("Simple cell".equals(cell.getName()));
		
		List<Segment> segList = cell.getSegmentList();
		assertTrue(segList.size() == 2);
		
		Segment seg1 = segList.get(0);
		assertTrue(seg1.getId().equals(new BigInteger("0")));
		assertTrue("Soma".equals(seg1.getName()));
				
		Point p1 = seg1.getProximal();
		assertTrue((p1.getX() == 0.0) && (p1.getY() == 0.0) && (p1.getZ() == 0.0));
		assertTrue(p1.getDiameter() == 10.0);
		Point d1 = seg1.getDistal();
		assertTrue((d1.getX() == 10.0) && (d1.getY() == 0.0) && (d1.getZ() == 0.0));
		assertTrue(d1.getDiameter() == 10.0);
		
		Segment seg2 = segList.get(1);
		assertTrue(seg2.getId().equals(new BigInteger("1")));
		assertTrue("Dendrite".equals(seg2.getName()));
		
		Point p2 = seg2.getProximal();
		assertTrue((p2.getX() == 10.0) && (p2.getY() == 0.0) && (p2.getZ() == 0.0));
		assertTrue(p2.getDiameter() == 3.0);
		Point d2 = seg2.getDistal();
		assertTrue((d2.getX() == 20.0) && (d2.getY() == 0.0) && (d2.getZ() == 0.0));
		assertTrue(d2.getDiameter() == 3.0);
		
		List<Cable> cblList = cell.getCableList();
		assertTrue(cblList.size() == 2);
		
		Cable cbl1 = cblList.get(0);
		assertTrue("SomaCable".equals(cbl1.getName()));
		assertTrue(cbl1.getId().equals(new BigInteger("0")));
		List<String> groups = cbl1.getGroup();
		assertTrue(groups.size() == 1);
		assertTrue("soma_group".equals(groups.get(0)));
		
		Cable cbl2 = cblList.get(1);
		assertTrue("DendriteCable".equals(cbl2.getName()));
		assertTrue(cbl2.getId().equals(new BigInteger("1")));
		groups = cbl2.getGroup();
		assertTrue(groups.size() == 1);
		assertTrue("dendrite_group".equals(groups.get(0)));		
	}
	
	//incomplete
	@Test public void testMossyMorphUnmarshalling() throws Exception
	{
		NeuroMLConverter conv = new NeuroMLConverter();
		
		String wdir = System.getProperty("user.dir");
		String tdir = wdir + File.separator + "src/test/resources";
		String fpath = tdir + File.separator +"MossyCellBiophys.xml";

        System.out.println("Performing test on "+fpath);
        
		NeuroMLLevel3 morph = conv.readNeuroML(fpath);		
		assertNotNull(morph);
		
		List<Level3Cell> clist = morph.getCellList();
		Level3Cell mcell = clist.get(0);
		assertNotNull(mcell);
		
		String notes = mcell.getNotes();
		
		assertTrue("An abstracted Mossy Cell morphology from Santhakumar et al 2005 including biophysical parameters".equals(notes));
	}

}
