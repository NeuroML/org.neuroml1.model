package org.neuroml1.model.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.neuroml1.model.NeuroMLLevel3;
import org.neuroml1.model.channel.ChannelML;
import org.neuroml1.model.morph.Morphology;
import org.neuroml1.model.network.NetworkML;

public class NeuroMLConverter
{
	protected JAXBContext jaxb;
	
	protected Marshaller marshallerNeuroML;
	protected Marshaller marshallerChannelML;
	protected Marshaller marshallerMorphML;
	protected Marshaller marshallerNetworkML;
	
	protected Unmarshaller unmarshaller;	
    
    
    public static String NEUROML_NAMESPACE_URI = "http://morphml.org/neuroml/schema";
    public static String MORPHML_NAMESPACE_URI = "http://morphml.org/morphml/schema";
    public static String CHANNELML_NAMESPACE_URI = "http://morphml.org/channelml/schema";
    public static String NETWORKML_NAMESPACE_URI = "http://morphml.org/networkml/schema";


    public static String NEUROML_SCHEMA_LOCATION = "http://www.neuroml.org/NeuroMLValidator/NeuroMLFiles/Schemata/v1.8.1/Level3/NeuroML_Level3_v1.8.1.xsd";
    public static String MORPHML_SCHEMA_LOCATION = "http://www.neuroml.org/NeuroMLValidator/NeuroMLFiles/Schemata/v1.8.1/Level1/MorphML_v1.8.1.xsd";
    public static String CHANNELML_SCHEMA_LOCATION = "http://www.neuroml.org/NeuroMLValidator/NeuroMLFiles/Schemata/v1.8.1/Level2/ChannelML_v1.8.1.xsd";
    public static String NETWORKML_SCHEMA_LOCATION = "http://www.neuroml.org/NeuroMLValidator/NeuroMLFiles/Schemata/v1.8.1/Level3/NetworkML_v1.8.1.xsd";
	
	
	public NeuroMLConverter() throws Exception
	{
		jaxb = JAXBContext.newInstance("org.neuroml1.model:org.neuroml1.model.morph:org.neuroml1.model.meta:org.neuroml1.model.channel:org.neuroml1.model.bio:org.neuroml1.model.network");
		//FIXME Removed :neuroml2
		
		marshallerMorphML = jaxb.createMarshaller();		
		marshallerMorphML.setProperty("com.sun.xml.bind.namespacePrefixMapper", NeuroMLNamespacePrefixMapper.getMorphMLMapper());
		marshallerMorphML.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		marshallerMorphML.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, MORPHML_NAMESPACE_URI+" "+MORPHML_SCHEMA_LOCATION);
		
		marshallerChannelML = jaxb.createMarshaller();		
		marshallerChannelML.setProperty("com.sun.xml.bind.namespacePrefixMapper", NeuroMLNamespacePrefixMapper.getChannelMLMapper());
		marshallerChannelML.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		marshallerChannelML.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, CHANNELML_NAMESPACE_URI+" "+CHANNELML_SCHEMA_LOCATION);
		
		marshallerNetworkML = jaxb.createMarshaller();		
		marshallerNetworkML.setProperty("com.sun.xml.bind.namespacePrefixMapper", NeuroMLNamespacePrefixMapper.getNetworkMLMapper());
		marshallerNetworkML.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		marshallerNetworkML.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, NETWORKML_NAMESPACE_URI+" "+NETWORKML_SCHEMA_LOCATION);
		
		marshallerNeuroML = jaxb.createMarshaller();		
		marshallerNeuroML.setProperty("com.sun.xml.bind.namespacePrefixMapper", NeuroMLNamespacePrefixMapper.getNeuroMLLevel3Mapper());
		marshallerNeuroML.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		marshallerNeuroML.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, NEUROML_NAMESPACE_URI+" "+NEUROML_SCHEMA_LOCATION);
		
		unmarshaller = jaxb.createUnmarshaller();
	}
	
	public NeuroMLLevel3 readNeuroML(String xmlFile) throws Exception
	{
		File f = new File(xmlFile);
		if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
		
		@SuppressWarnings("unchecked")
		JAXBElement<NeuroMLLevel3> jbe =
			(JAXBElement<NeuroMLLevel3>) unmarshaller.unmarshal(f);
		
		return jbe.getValue();		
	}
	
	
	
	public Morphology xmlToMorphology(String xmlFile) throws Exception
	{
		File f = new File(xmlFile);
		if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
		
		@SuppressWarnings("unchecked")
		JAXBElement<Morphology> jbe =
			(JAXBElement<Morphology>) unmarshaller.unmarshal(f);
		
		return jbe.getValue();		
	}
	
	public ChannelML xmlToChannel(String xmlfile) throws Exception
	{
		File f = new File(xmlfile);
		if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
		
		@SuppressWarnings("unchecked")
		JAXBElement<ChannelML> jbe =
			(JAXBElement<ChannelML>) unmarshaller.unmarshal(f);
		
		return jbe.getValue();
	}
	
	public NetworkML xmlToNetwork(String xmlfile) throws Exception
	{
		File f = new File(xmlfile);
		if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
		
		@SuppressWarnings("unchecked")
		JAXBElement<NetworkML> jbe =
			(JAXBElement<NetworkML>) unmarshaller.unmarshal(f);
		
		return jbe.getValue();
	}
	
	public void morphologyToXml(Morphology morph, String filename) throws Exception
	{
		JAXBElement<Morphology> jbm =
			new JAXBElement<Morphology>(new QName("morphml"),
					Morphology.class,morph);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

		marshallerMorphML.marshal(jbm, baos);

        String withNs = baos.toString();
        String correctNs = withNs.replaceAll(NeuroMLNamespacePrefixMapper.TEMP_NAMESPACE+":", "");
        correctNs = correctNs.replaceAll(":"+NeuroMLNamespacePrefixMapper.TEMP_NAMESPACE, "");
        
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);

        fos.write(correctNs.getBytes());
		fos.close();
	}
	
	public void channelToXml(ChannelML chan, String filename) throws Exception
	{
		JAXBElement<ChannelML> jbc =
			new JAXBElement<ChannelML>(new QName("channelml"),
					ChannelML.class, chan);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

		marshallerChannelML.marshal(jbc, baos);

        String withNs = baos.toString();
        String correctNs = withNs.replaceAll(NeuroMLNamespacePrefixMapper.TEMP_NAMESPACE+":", "");
        correctNs = correctNs.replaceAll(":"+NeuroMLNamespacePrefixMapper.TEMP_NAMESPACE, "");
        
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);

        fos.write(correctNs.getBytes());
		fos.close();
	}
	
	public void networkToXml(NetworkML net, String filename) throws Exception
	{
		JAXBElement<NetworkML> jbn =
			new JAXBElement<NetworkML>(new QName("networkml"),
					NetworkML.class, net);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

		marshallerNetworkML.marshal(jbn, baos);

        String withNs = baos.toString();
        String correctNs = withNs.replaceAll(NeuroMLNamespacePrefixMapper.TEMP_NAMESPACE+":", "");
        correctNs = correctNs.replaceAll(":"+NeuroMLNamespacePrefixMapper.TEMP_NAMESPACE, "");
        
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);

        fos.write(correctNs.getBytes());
		fos.close();
	}

	public void neuromlToXml(NeuroMLLevel3 l3, String filename) throws Exception
	{
		JAXBElement<NeuroMLLevel3> jbc =
			new JAXBElement<NeuroMLLevel3>(new QName("neuroml"),
					NeuroMLLevel3.class, l3);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

		marshallerNeuroML.marshal(jbc, baos);

        String withNs = baos.toString();
        String correctNs = withNs.replaceAll(NeuroMLNamespacePrefixMapper.TEMP_NAMESPACE+":", "");
        correctNs = correctNs.replaceAll(":"+NeuroMLNamespacePrefixMapper.TEMP_NAMESPACE, "");
        
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);

        fos.write(correctNs.getBytes());
		fos.close();
	}

}
