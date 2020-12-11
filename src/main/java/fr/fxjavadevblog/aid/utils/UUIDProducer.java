package fr.fxjavadevblog.aid.utils;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


/**
 * UUID CDI Producer and static method invocation.
 * 
 * @see InjectUUID
 * @author François-Xavier Robin
 *
 */
@ApplicationScoped
public class UUIDProducer
{
    /**
     * produces randomly generated UUID for primary keys for CDI Injection.
     *
     * @return UUID as a HEXA-STRING
     *
     */
    @Produces
    @InjectUUID
    public String produceUUIDAsString()
    {
        return UUIDProducer.getUUIDAsString();
    }
    
    /**
     * produces randomly generated UUID for primary keys.
     *
     * @return UUID as a HEXA-STRING
     *
     */
    public static String getUUIDAsString()
    {
    	return UUID.randomUUID().toString();
    }
}
