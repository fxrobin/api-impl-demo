package fr.fxjavadevblog.aid.utils;

import java.util.List;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

/**
 * Utility class for http request parameters.
 */
public class QueryParameterUtils {

    private QueryParameterUtils()
    {
        // Protection
    }
     
    /**
     * constructs a Sort instance given the HTTP query parameter formatted as "+fieldName1,-fieldName2,fieldName3".
     * If "-" is specified, then the direction is DESCENDING. If "+" or none, then the direction is "ASCENDING".
     * 
     * @param paramValues
     *      HTTP parameter.
     * @return
     *      Instance of Sort.
     */
    public static Sort createSort(List<String> paramValues)
    {
        Sort sort = Sort.by();

        if (paramValues != null && !paramValues.isEmpty())
        { 
            for(String param : paramValues)
            {
                Direction direction = param.startsWith("-") ? Direction.Descending : Direction.Ascending;
                String field = (param.startsWith("-") || param.startsWith("+")) ? param.substring(1) : param;
                sort = sort.and(field, direction);
            }        
        }

        return sort;
    }
    
}