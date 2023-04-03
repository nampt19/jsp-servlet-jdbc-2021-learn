package pageAndSort;

public class PageRequest implements Pageable {
    Integer page;
    Integer limit;
    Sorter sorter;

    public PageRequest(Integer page, Integer limit, Sorter sorter) {
        this.page = page;
        this.limit = limit;
        this.sorter = sorter;
    }

    @Override
    public Integer getPage() {
        return this.page;
    }

    @Override
    public Integer getOffset() {
        if (this.page != null && this.limit != null) {
            return (this.page - 1) * this.limit;
        }
        return null;
    }

    @Override
    public Integer getLimit() {
        return this.limit;
    }

    @Override
    public Sorter getSorter() {
        if (this.sorter != null) {
            return this.sorter;
        }
        return null;
    }
}
