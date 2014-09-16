package org.neuroml1.model.util;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/*        
 * TODO: Needs to be moved to a separate package for validation!
 */
public class NeuroML1Validator {

    boolean allTestsPassed = true;

    public static final String NML1_META_SCHEMA =  "/NeuroML1Schemas/Level1/Metadata_v1.8.1.xsd";
    public static final String NML1_MORPH_SCHEMA = "/NeuroML1Schemas/Level1/MorphML_v1.8.1.xsd";
    public static final String NML1_BIO_SCHEMA =   "/NeuroML1Schemas/Level2/Biophysics_v1.8.1.xsd";
    public static final String NML1_CHAN_SCHEMA =  "/NeuroML1Schemas/Level2/ChannelML_v1.8.1.xsd";
    public static final String NML1_NET_SCHEMA =   "/NeuroML1Schemas/Level3/NetworkML_v1.8.1.xsd";
    public static final String NML1_L3_SCHEMA =    "/NeuroML1Schemas/Level3/NeuroML_Level3_v1.8.1.xsd";

    public NeuroML1Validator() {

    }

    public boolean validateWithTests(File xmlFile) throws SAXException, IOException, JAXBException {
        String main_schema_name = NML1_L3_SCHEMA;

        System.out.println("Testing validity of: " + xmlFile.getAbsolutePath() + " against: " + main_schema_name);

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        StreamSource meta_schema = new StreamSource(getClass().getResourceAsStream(NML1_META_SCHEMA));
        StreamSource morph_schema = new StreamSource(getClass().getResourceAsStream(NML1_MORPH_SCHEMA));
        StreamSource bio_schema = new StreamSource(getClass().getResourceAsStream(NML1_BIO_SCHEMA));
        StreamSource chan_schema = new StreamSource(getClass().getResourceAsStream(NML1_CHAN_SCHEMA));
        StreamSource net_schema = new StreamSource(getClass().getResourceAsStream(NML1_NET_SCHEMA));
        StreamSource main_schema = new StreamSource(getClass().getResourceAsStream(main_schema_name));

        Schema schema = factory.newSchema(new Source[]{meta_schema, morph_schema, bio_schema, chan_schema, net_schema, main_schema});

        Validator validator = schema.newValidator();
        Source xmlFileSource = new StreamSource(xmlFile);

        validator.validate(xmlFileSource);

        System.out.println("File: " + xmlFile.getAbsolutePath() + " is valid!!");

        return true;

    }

    public static void main(String[] args) throws SAXException, IOException, JAXBException {

        System.out.println("Testing it...");
        NeuroML1Validator nv = new NeuroML1Validator();

        nv.validateWithTests(new File("/home/padraig/NeuroMLValidator/web/NeuroMLFiles/Examples/MorphML/Simple.morph.xml"));

        nv.validateWithTests(new File("/home/padraig/neuroConstruct/NeuroML2/examples/NMLv1.x/GranuleCell.xml"));

    }

}
