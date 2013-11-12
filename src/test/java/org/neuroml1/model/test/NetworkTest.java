package org.neuroml1.model.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigInteger;
import java.util.List;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.neuroml1.model.Level3Cell;
import org.neuroml1.model.NeuroMLLevel3;
import org.neuroml1.model.meta.Point3D;
import org.neuroml1.model.network.Instances;
import org.neuroml1.model.network.CellInstance;
import org.neuroml1.model.network.NetworkML;
import org.neuroml1.model.network.Population;
import org.neuroml1.model.network.Projection;
import static org.neuroml1.model.test.ChannelTest.getTempDir;
import org.neuroml1.model.util.NeuroML1ValidatorTest;
import org.neuroml1.model.util.NeuroMLConverter;

public class NetworkTest
{
	@Test public void testNetworkMarshalling() throws Exception
	{
		NeuroMLLevel3 l3 = new NeuroMLLevel3();
        Population p1 = new Population();
        p1.setName("pop1");
        p1.setCellType(new Population.CellTypeElement("CellTypeA"));
        l3.getPopulationList().add(p1);
        int instances = 10;
        Instances insts = new Instances();
        insts.setSize(BigInteger.valueOf(instances));
        p1.setInstances(insts);
        
        for (int i=0;i<instances;i++) {
            CellInstance in = new CellInstance();
            in.setId(BigInteger.valueOf(i));
            Point3D location = new Point3D();
            location.setX(0);
            location.setY(i*10);
            location.setZ(i*10);
            in.setLocation(location);
            insts.getInstance().add(in);
            
        }
        
		NeuroMLConverter conv = new NeuroMLConverter();

        File netFile = new File(getTempDir(),"net-as-networkml.xml");
        NetworkML netml = new NetworkML();
        netml.setPopulations(l3.getPopulations());
        
		conv.networkToXml(netml, netFile.getAbsolutePath());
        System.out.println("Saved to a file: "+ netFile);
		assertTrue(netFile.exists());
        
        NeuroML1ValidatorTest.testValidate(netFile);

        netFile = new File(getTempDir(),"net-as-neuroml.xml");
		conv.neuromlToXml(l3, netFile.getAbsolutePath());
        System.out.println("Saved to a file: "+ netFile);
		assertTrue(netFile.exists());
        
        NeuroML1ValidatorTest.testValidate(netFile);
        
	}
	
	
	@Test public void testCompleteNetwork() throws Exception
	{
		NeuroMLConverter conv = new NeuroMLConverter();
		
		String wdir = System.getProperty("user.dir");
		String tdir = wdir + File.separator + "src/test/resources";
		String fpath = tdir + File.separator +"CompleteNetwork.xml";

        System.out.println("Performing test on "+fpath);
		
		NeuroMLLevel3 l3 = conv.readNeuroML(fpath);
		
		List<Level3Cell> l3cells = l3.getCellList();
		assertTrue(l3cells.size() == 1);
	
		List<Population> poplist = l3.getPopulationList();
		assertTrue(poplist.size() == 2);
		
		List<Projection> projlist = l3.getProjectionList();
		assertTrue(projlist.size() == 1);
		
	}
	

}
