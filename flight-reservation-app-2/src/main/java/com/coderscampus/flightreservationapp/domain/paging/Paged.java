package com.coderscampus.flightreservationapp.domain.paging;
import org.springframework.data.domain.Page;

/**
 * class that holds the result of a page search in the database and its paging info
 *
 */
public class Paged<T> {

    private Page<T> page;

    private Paging paging;

    public Paged(Page<T> page, Paging paging) {
        this.page = page;
        this.paging = paging;
    }

    public Paged() {
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
