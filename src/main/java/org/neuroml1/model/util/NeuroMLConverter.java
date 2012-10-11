package org.neuroml1.model.util;

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
	
	protected Marshaller marshaller;
	
	protected Unmarshaller unmarshaller;	
	
	
	public NeuroMLConverter() throws Exception
	{
		jaxb = JAXBContext.newInstance("org.neuroml.model:org.neuroml.model.morph:org.neuroml.model.meta:org.neuroml.model.channel:org.neuroml.model.bio:org.neuroml.model.network");
		//FIXME Removed :neuroml2
		
		marshaller = jaxb.createMarshaller();		
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",new NeuroMLNamespacePrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		unmarshaller = jaxb.createUnmarshaller();
	}
	
	public NeuroMLLevel3 readNeuroML(String xmlFile) throws Exception
	{
		File f = new File(xmlFile);
		if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
		
		JAXBElement<NeuroMLLevel3> jbe =
			(JAXBElement<NeuroMLLevel3>) unmarshaller.unmarshal(f);
		
		return jbe.getValue();		
	}
	
	
	
	public Morphology xmlToMorphology(String xmlFile) throws Exception
	{
		File f = new File(xmlFile);
		if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
		
		JAXBElement<Morphology> jbe =
			(JAXBElement<Morphology>) unmarshaller.unmarshal(f);
		
		return jbe.getValue();		
	}
	
	public ChannelML xmlToChannel(String xmlfile) throws Exception
	{
		File f = new File(xmlfile);
		if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
		
		JAXBElement<ChannelML> jbe =
			(JAXBElement<ChannelML>) unmarshaller.unmarshal(f);
		
		return jbe.getValue();
	}
	
	public NetworkML xmlToNetwork(String xmlfile) throws Exception
	{
		File f = new File(xmlfile);
		if (!f.exists()) throw new FileNotFoundException(f.getAbsolutePath());
		
		JAXBElement<NetworkML> jbe =
			(JAXBElement<NetworkML>) unmarshaller.unmarshal(f);
		
		return jbe.getValue();
	}
	
	public void morphologyToXml(Morphology morph, String filename) throws Exception
	{
		JAXBElement<Morphology> jbm =
			new JAXBElement<Morphology>(new QName("morphml"),
					Morphology.class,
					                    morph);
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);
				
		marshaller.marshal(jbm, fos);
		fos.close();
	}
	
	public void channelToXml(ChannelML chan, String filename) throws Exception
	{
		JAXBElement<ChannelML> jbc =
			new JAXBElement<ChannelML>(new QName("channelml"),
					ChannelML.class,
					                    chan);
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);
				
		marshaller.marshal(jbc, fos);
		fos.close();
	}

	public void neuromlToXml(NeuroMLLevel3 l3, String filename) throws Exception
	{
		JAXBElement<NeuroMLLevel3> jbc =
			new JAXBElement<NeuroMLLevel3>(new QName("neuroml"),
					NeuroMLLevel3.class,
					                    l3);
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);

		marshaller.marshal(jbc, fos);
		fos.close();
	}

}
