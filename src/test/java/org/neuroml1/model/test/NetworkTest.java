package org.neuroml1.model.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.neuroml1.model.Level3Cell;
import org.neuroml1.model.NeuroMLLevel3;
import org.neuroml1.model.network.Population;
import org.neuroml1.model.network.Projection;
import org.neuroml1.model.util.NeuroMLConverter;

public class NetworkTest
{
	@Test public void testNetworkMarshalling() throws Exception
	{
		NeuroMLConverter conv = new NeuroMLConverter();
		
		String wdir = System.getProperty("user.dir");
		String tdir = wdir + File.separator + "src/test/resources";
		String fpath = tdir + File.separator +"SimpleNetworkInstance.xml";

        System.out.println("Performing test on "+fpath);

		conv.xmlToNetwork(fpath);
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
