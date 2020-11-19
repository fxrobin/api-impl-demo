package fr.fxjavadevblog.aid.utils;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


/**
 * UUID CDI Producer.
 * 
 * @see InjectUUID
 * @author François-Xavier Robin
 *
 */
@ApplicationScoped
public class UUIDProducer
{
    /**
     * produces randomly generated UUID for primary keys.
     *
     * @return UUID as a HEXA-STRING
     *
     */
    @Produces
    @InjectUUID
    public String produceUUIDAsString()
    {
        return UUID.randomUUID().toString();
    }
}
