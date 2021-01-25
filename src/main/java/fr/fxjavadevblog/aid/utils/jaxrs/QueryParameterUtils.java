package fr.fxjavadevblog.aid.utils.jaxrs;

import java.util.List;

import fr.fxjavadevblog.aid.utils.commons.Splitter;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for http request parameters.
 */
@Slf4j
public class QueryParameterUtils {

	private QueryParameterUtils() {
		// Protection
	}

	/**
	 * constructs a Sort instance given the HTTP query parameter formatted as
	 * "+fieldName1,-fieldName2,fieldName3". If "-" is specified, then the direction
	 * is DESCENDING. If "+" or none, then the direction is "ASCENDING".
	 * 
	 * @param paramValues HTTP parameter.
	 * @return Instance of Sort.
	 */
	public static Sort createSort(String sortClause) {
		Sort sort = Sort.by();

		List<String> paramValues = Splitter.split(sortClause);

		if (paramValues != null && !paramValues.isEmpty()) {
			for (String param : paramValues) {
				Direction direction = param.startsWith("-") ? Direction.Descending : Direction.Ascending;
				String field = (param.startsWith("-") || param.startsWith("+")) ? param.substring(1) : param;
				field = field.trim();
				sort = sort.and(field, direction);
				log.info("Sort clause : [param={} | field={} | direction={}]", param, field, direction);
			}
		}

		return sort;
	}

}