package fr.fxjavadevblog.aid.utils;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

public final class PagedQueryWrapper {
	
	private PagedQueryWrapper() {
		// Protection
	}
	
	public static <T> PagedResponse<List<T>> wrap(PanacheQuery<T> query) {
		
		int currentPage = query.page().index;
		
		PagedMetadata metadata = PagedMetadata.builder()
    										  .resourceCount(query.count())
    										  .pageCount(query.pageCount())
    										  .currentPage(currentPage)
    										  .build();
    	
    	return PagedResponse.<List<T>>builder()
    					    .metadata(metadata)
    					    .data(query.list())
    					    .build();
	}
}
