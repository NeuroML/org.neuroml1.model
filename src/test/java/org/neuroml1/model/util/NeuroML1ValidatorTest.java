/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.neuroml1.model.util;

import java.io.File;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author padraig
 */
public class NeuroML1ValidatorTest
{
    
    
    @Test public void testValidityLocalFiles() throws Exception
    {
		String wdir = System.getProperty("user.dir");
		String tdir = wdir + File.separator + "src/test/resources";
        
		testValidate(new File(tdir + File.separator +"CompleteNetwork.xml"));
		testValidate(new File(tdir + File.separator +"KCa_chan.xml"));
		testValidate(new File(tdir + File.separator +"MossyCellBiophys.xml"));
		testValidate(new File(tdir + File.separator +"Simple.morph.xml"));
		testValidate(new File(tdir + File.separator +"SimpleNetworkInstance.xml"));
        
    }

    
    public static void testValidate(File f) throws Exception
    {
        System.out.println("Performing test on "+f);

		assertTrue(f.exists());

		NeuroML1Validator val = new NeuroML1Validator();

		assertTrue(val.validateWithTests(f));
    }
    
}
