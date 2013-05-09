package org.neuroml1.model.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/*
 *        
 *        WORK IN PROGRESS!!!
 *        
 * TODO: Needs to be moved to a separate package for validation!
 */
public class NeuroML1Validator {

	boolean allTestsPassed = true;
	
	public NeuroML1Validator() {
		
	}
	

	public boolean validateWithTests(File xmlFile) throws SAXException, IOException, JAXBException
	{
		InputStream in = getClass().getResourceAsStream("/NeuroML1Schemas/Level3/NeuroML_Level3_v1.8.1.xsd");

		//testValidity(xmlFile, new StreamSource(in));

        
        //TODO: replace!!
		String wdir = System.getProperty("user.dir");
		File xsdFile = new File(wdir + File.separator + "src/main/resources/NeuroML1Schemas/Level3/NeuroML_Level3_v1.8.1.xsd");
        if (!xsdFile.exists())
        {
            //Temp hack...
            String jnmlHome = System.getenv("JNML_HOME");
            xsdFile = new File(jnmlHome + File.separator +".." + File.separator + "org.neuroml1.model/src/main/resources/NeuroML1Schemas/Level3/NeuroML_Level3_v1.8.1.xsd");
            if (!xsdFile.exists())
            {
                //Temp hack...
                xsdFile = new File(jnmlHome + File.separator +"../.." + File.separator + "org.neuroml1.model/src/main/resources/NeuroML1Schemas/Level3/NeuroML_Level3_v1.8.1.xsd");
            }
        }
        validateAgainstSchema(xmlFile, xsdFile);
 
		
		//NeuroMLConverter conv = new NeuroMLConverter();
		//NeuroMLLevel3 nml = conv.NeuroMLLevel3(xmlFile);
		//return validateWithTests(nml2);
		
		return true;
		
	}
	/*
	public boolean validateWithTests(NeuroMLDocument nml2)
	{
		
		// Checks the areas the Schema just can't reach...
		
		//////////////////////////////////////////////////////////////////
		// <cell>
		//////////////////////////////////////////////////////////////////
		
		for (Cell cell: nml2.getCell()){
			
			// Morphologies
			ArrayList<Integer> segIds = new ArrayList<Integer>();
			boolean rootFound = false;
			if (cell.getMorphology() != null) {
				for(Segment segment: cell.getMorphology().getSegment()) {
					int segId = Integer.parseInt(segment.getId());
					
					test(10000, "No repeated segment Ids allowed within a cell", "Current segment ID: "+segId, !segIds.contains(segId));
					segIds.add(segId);
					
					if (segId==0){
						rootFound = true;
					}
				}

				test(10001,"Root segment has id == 0", "", rootFound);
			} else {
				//TODO: test for morphology attribute!
			}
			
		}
		
		return allTestsPassed;
		
	}
	
	private void test(int id, String testName, String info, boolean test) {
		if (!test) {
	        System.out.println("Test: "+id+" ("+testName+") failed! .. "+info);
	        allTestsPassed = false;
		} else {
	        //System.out.println("Test: "+id+" ("+testName+") succeeded! "+info);
		}
	}*/


	public static void testValidity(File xmlFile, String xsdFile) throws SAXException, IOException {
		StreamSource schemaFileSource = new StreamSource(xsdFile);
		testValidity(xmlFile, schemaFileSource);
	}

    public static void testValidity(File xmlFile, StreamSource schemaFileSource) throws SAXException, IOException {
		System.out.println("Testing validity of: "+ xmlFile.getAbsolutePath()+" against: " +schemaFileSource.getSystemId());

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema = factory.newSchema(schemaFileSource);

        Validator validator = schema.newValidator();

        Source xmlFileSource = new StreamSource(xmlFile);

        validator.validate(xmlFileSource);

        System.out.println("File: "+ xmlFile.getAbsolutePath()+" is valid!!");
	}
    
    public static void validateAgainstSchema(File xmlFile, File schemaFile) throws SAXException, IOException
    {
		System.out.println("Testing validity of: "+ xmlFile.getAbsolutePath()+" against: " +schemaFile.getAbsolutePath());
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Source schemaFileSource = new StreamSource(schemaFile);
   
        Schema schema = factory.newSchema(schemaFileSource);
        Validator validator = schema.newValidator();
        Source xmlFileSource = new StreamSource(xmlFile);

        validator.validate(xmlFileSource);
        
        System.out.println("File: "+ xmlFile.getAbsolutePath()+" is valid!!");
 
    }
    

    public static void main(String[] args) throws SAXException, IOException, JAXBException {

		System.out.println("Testing it...");
		NeuroML1Validator nv = new NeuroML1Validator();
		nv.validateWithTests(new File("/home/padraig/neuroConstruct/NeuroML2/examples/NMLv1.x/GranuleCell.xml"));

	}

}
