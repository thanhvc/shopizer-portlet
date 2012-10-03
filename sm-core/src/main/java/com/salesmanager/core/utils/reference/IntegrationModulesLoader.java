package com.salesmanager.core.utils.reference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.system.model.IntegrationModule;
import com.salesmanager.core.business.system.model.ModuleConfig;

@Component
public class IntegrationModulesLoader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationModulesLoader.class);
	

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<IntegrationModule> loadIntegrationModules(String jsonFilePath) throws Exception {
		
		
		List<IntegrationModule> modules = new ArrayList<IntegrationModule>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
            InputStream in =
                this.getClass().getClassLoader().getResourceAsStream(jsonFilePath);
			
            
            Map[] objects = mapper.readValue(in, Map[].class);
            
            for(int i = 0; i < objects.length; i++) {
            	
            	
            	Map object = objects[i];
            	
            	IntegrationModule module = new IntegrationModule();
            	module.setModule((String)object.get("module"));
            	module.setCode((String)object.get("code"));
            	module.setImage((String)object.get("image"));
            	
            	//module.setRegions(regions)
            	
            	
            	
            	List confs = (List)object.get("configuration");
            	
            	//convert to json
            	
            	
            	
            	if(confs!=null) {
            		StringBuilder configString = new StringBuilder();
            		configString.append("[");
            		Map<String,ModuleConfig> moduleConfigs = new HashMap<String,ModuleConfig>();
	            	int count=0;
            		for(Object oo : confs) {
	            		
	            		Map values = (Map)oo;
	            		
	            		String env = (String)values.get("env");
	            		
	            		ModuleConfig config = new ModuleConfig();
	            		config.setScheme((String)values.get("scheme"));
	            		config.setHost((String)values.get("host"));
	            		config.setPort((String)values.get("port"));
	            		config.setUri((String)values.get("uri"));
	            		config.setEnv((String)values.get("env"));
	            		
	            		String jsonConfigString = mapper.writeValueAsString(config);
	            		configString.append(jsonConfigString);
	            		
	            		moduleConfigs.put(env, config);
	            		
	            		if(count<(confs.size()-1)) {
	            			configString.append(",");
	            		}
	            		count++;
	            		
	            		
	            	}
	            	configString.append("]");
	            	module.setConfiguration(configString.toString());
	            	module.setModuleConfigs(moduleConfigs);
            	}
            	
            	List<String> regions = (List<String>)object.get("regions");
            	if(regions!=null) {
            		

            		StringBuilder configString = new StringBuilder();
            		configString.append("[");
            		int count=0;
            		for(String region : regions) {
            			
            			module.getRegionsSet().add(region);
            			String jsonConfigString = mapper.writeValueAsString(region);
            			configString.append(jsonConfigString);
            			
	            		if(count<(regions.size()-1)) {
	            			configString.append(",");
	            		}
	            		count++;

            		}
            		configString.append("]");
            		module.setRegions(configString.toString());

            	}
            	
            	modules.add(module);
            	
            }
            
            return modules;

  		} catch (Exception e) {
  			throw new ServiceException(e);
  		}
  		
  		

		
	
	
	
	}

}