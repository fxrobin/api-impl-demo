package fr.fxjavadevblog.aid.utils.pagination;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

public final class PagedQueryWrapper {
	
	private PagedQueryWrapper() {
		// Protection
	}
	
	public static <T> PagedResponse<T> wrap(PanacheQuery<T> query) {
		
		int currentPage = query.page().index;
		
		Metadata metadata = Metadata.builder()
    										  .resourceCount(query.count())
    										  .pageCount(query.pageCount())
    										  .currentPage(currentPage)
    										  .build();
    	
		List<T> results = query.list();
		
    	return PagedResponse.<T>builder()
    					    .metadata(metadata)
    					    .data(results)
    					    .build();
	}
}
