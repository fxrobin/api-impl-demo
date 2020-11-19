package fr.fxjavadevblog.aid.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.ext.ParamConverter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.fxjavadevblog.aid.utils.GenericEnumConverter;

class GenericEnumConverterTest
{
    public enum DummyEnum
    {
        @JsonProperty("value-1")
        VALUE_1,

        @JsonProperty("value-2")
        VALUE_2,

        @JsonProperty("value-3")
        VALUE_3,

        @JsonProperty("value-4")
        VALUE_4,

        @JsonProperty("value-5")
        VALUE_5,
        
        // without annotation intentionaly, to check automatic conversion.
        VALUE_6;     
    }

    static private ParamConverter<DummyEnum> converter;
    
    @BeforeAll
    static void init()
    {
        converter = GenericEnumConverter.of(DummyEnum.class);
    }
    
    
    @DisplayName("Enum to Json via GenericEnumConverter")
    @Test
    void testEnumToJson()
    {
        assertEquals("value-1", converter.toString(DummyEnum.VALUE_1));
        assertEquals("value-2", converter.toString(DummyEnum.VALUE_2));
        assertEquals("value-3", converter.toString(DummyEnum.VALUE_3));
        assertEquals("value-4", converter.toString(DummyEnum.VALUE_4));
        assertEquals("value-5", converter.toString(DummyEnum.VALUE_5));
        assertEquals("value-6", converter.toString(DummyEnum.VALUE_6));
    }

 
    @DisplayName("Json to Enum via GenericEnumConverter")
    @Test
    void testJsonToEnum()
    {
        assertEquals(DummyEnum.VALUE_1, converter.fromString("value-1"));
        assertEquals(DummyEnum.VALUE_2, converter.fromString("value-2"));
        assertEquals(DummyEnum.VALUE_3, converter.fromString("value-3"));
        assertEquals(DummyEnum.VALUE_4, converter.fromString("value-4"));
        assertEquals(DummyEnum.VALUE_5, converter.fromString("value-5"));
        assertEquals(DummyEnum.VALUE_6, converter.fromString("value-6"));
    }

}
