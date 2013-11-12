package org.neuroml1.model.util;

import java.util.*;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class NeuroMLNamespacePrefixMapper extends NamespacePrefixMapper
{
	protected Map<String, String> namespaceToPrefixMap;
    
    public static final String TEMP_NAMESPACE = "----TEMP--NAMESPACE---";
	
	private NeuroMLNamespacePrefixMapper()
	{		
    }
    
    public static NeuroMLNamespacePrefixMapper getNeuroMLLevel3Mapper() 
    {
        NeuroMLNamespacePrefixMapper nm = new NeuroMLNamespacePrefixMapper();
        
		nm.namespaceToPrefixMap = new HashMap<String, String>();
		nm.namespaceToPrefixMap.put("http://morphml.org/neuroml/schema", TEMP_NAMESPACE);
        
		nm.namespaceToPrefixMap.put("http://morphml.org/metadata/schema", "meta");
		nm.namespaceToPrefixMap.put("http://morphml.org/morphml/schema", "mml");
		nm.namespaceToPrefixMap.put("http://morphml.org/channelml/schema", "cml");
		nm.namespaceToPrefixMap.put("http://morphml.org/biophysics/schema", "bio");
		nm.namespaceToPrefixMap.put("http://morphml.org/networkml/schema", "net");
        
        return nm;
	}
    
    public static NeuroMLNamespacePrefixMapper getNetworkMLMapper() 
    {
        NeuroMLNamespacePrefixMapper nm = new NeuroMLNamespacePrefixMapper();
        
		nm.namespaceToPrefixMap = new HashMap<String, String>();
		nm.namespaceToPrefixMap.put("http://morphml.org/networkml/schema", TEMP_NAMESPACE);
        
		nm.namespaceToPrefixMap.put("http://morphml.org/metadata/schema", "meta");
		nm.namespaceToPrefixMap.put("http://morphml.org/morphml/schema", "mml");
		nm.namespaceToPrefixMap.put("http://morphml.org/channelml/schema", "cml");
		nm.namespaceToPrefixMap.put("http://morphml.org/biophysics/schema", "bio");
        
        return nm;
	}
    
    public static NeuroMLNamespacePrefixMapper getChannelMLMapper() 
    {
        NeuroMLNamespacePrefixMapper nm = new NeuroMLNamespacePrefixMapper();
        
		nm.namespaceToPrefixMap = new HashMap<String, String>();
		nm.namespaceToPrefixMap.put("http://morphml.org/channelml/schema", TEMP_NAMESPACE);
        
		nm.namespaceToPrefixMap.put("http://morphml.org/metadata/schema", "meta");
		nm.namespaceToPrefixMap.put("http://morphml.org/morphml/schema", "mml");
		nm.namespaceToPrefixMap.put("http://morphml.org/biophysics/schema", "bio");
        
        return nm;
	}
    
    public static NeuroMLNamespacePrefixMapper getMorphMLMapper() 
    {
        NeuroMLNamespacePrefixMapper nm = new NeuroMLNamespacePrefixMapper();
        
		nm.namespaceToPrefixMap = new HashMap<String, String>();
		nm.namespaceToPrefixMap.put("http://morphml.org/morphml/schema", TEMP_NAMESPACE);
        
		nm.namespaceToPrefixMap.put("http://morphml.org/metadata/schema", "meta");
        
        return nm;
	}
    
	
	
    @Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)	
	{
		if (namespaceToPrefixMap.containsKey(namespaceUri)) {
			return namespaceToPrefixMap.get(namespaceUri);
		}
		return suggestion;
	}
}
