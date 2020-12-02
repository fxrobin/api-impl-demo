package fr.fxjavadevblog.aid.utils;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

public class PagedQueryWrapper {
	
	public static PagedResponse<List<?>> wrap(PanacheQuery<?> query) {
		
		int page = query.page().index;
		
		PagedMetadata metadata = PagedMetadata.builder()
    										  .count(query.count())
    										  .pageCount(query.pageCount())
    										  .currentPage(page)
    										  .build();
    	
    	PagedResponse<List<?>> pageResponse = PagedResponse.<List<?>>builder()
    					.metadata(metadata)
    					.data(query.list())
    					.build();
    	
		return pageResponse;
	}
	

}
