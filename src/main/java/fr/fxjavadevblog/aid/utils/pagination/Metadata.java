package fr.fxjavadevblog.aid.utils.pagination;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Metadata {
	
	@Getter
	@Schema(description = "current diplayed page, starting at 0.")
	private int currentPage;
	
	@Getter
	@Schema(description = "total of pages for the query, starting at 0.")
	private int pageCount;
	
	@Getter
	@Schema(description = "total of resources for the query, starting at 0.")
	private long resourceCount;

}
