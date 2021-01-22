package fr.fxjavadevblog.aid.utils.pagination;

import lombok.Builder;
import lombok.Getter;

@Builder
public class PagedMetadata {
	
	@Getter
	private int currentPage;
	
	@Getter
	private int pageCount;
	
	@Getter
	private long resourceCount;

}
