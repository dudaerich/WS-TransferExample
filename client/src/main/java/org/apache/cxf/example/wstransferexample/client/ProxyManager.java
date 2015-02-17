package org.apache.cxf.example.wstransferexample.client;

import java.util.HashMap;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.JAXWSAConstants;
import org.apache.cxf.ws.transfer.resource.Resource;

/**
 * Class for holding and setup proxies.
 */
public class ProxyManager {

    private static ProxyManager instance = null;

    private  Map<String, Resource> proxies = new HashMap<String, Resource>();

    public static ProxyManager getInstance() {
        if (instance == null) {
            instance = new ProxyManager();
        }
        return instance;
    }

    public Resource getResource(EndpointReferenceType ref) {
        String address = ref.getAddress().getValue();
        Resource resource;
        if (proxies.containsKey(address)) {
            resource = proxies.get(address);
        } else {
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
            factory.setServiceClass(Resource.class);
            factory.setAddress(address);
            resource = (Resource) factory.create();
            proxies.put(address, resource);
        }
        AddressingProperties addrProps = new AddressingProperties();
        addrProps.setTo(ref);
        ((BindingProvider) resource).getRequestContext().put(JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES, addrProps);
        return resource;
    }

}
