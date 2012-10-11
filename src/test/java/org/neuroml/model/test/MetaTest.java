package org.neuroml.model.test;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.neuroml1.model.meta.ObjectFactory;
import org.neuroml1.model.meta.Point;
import org.neuroml1.model.meta.Points;

public class MetaTest
{
	@Test public void testPoints() throws Exception
	{		
		ObjectFactory ofac = new ObjectFactory();
		Points pnts = ofac.createPoints();
		List<Point> pntList = pnts.getPoint();
		
		Assert.assertTrue(pntList != null);
		Assert.assertTrue(pntList.size() == 0);		
		
		Point p1 = ofac.createPoint();
		Assert.assertTrue(p1 != null);
		p1.setDiameter(5.5);
		p1.setX(1.1);
		p1.setY(2.2);
		p1.setZ(3.3);
		
		pntList.add(p1);

        System.out.println("Finished basic test on meta");
	}
}
