//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pilot.tools.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageVo<T> implements BaseEntity {
    private List<T> records = new ArrayList();
    private long total = 0L;
    private long size = 10L;
    private long current = 1L;

    PageVo(final List<T> records, final long total, final long size, final long current) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
    }

    public static <T> PageVoBuilder<T> builder() {
        return new PageVoBuilder();
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageVo)) {
            return false;
        } else {
            PageVo<?> other = (PageVo) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label35:
                {
                    Object this$records = this.getRecords();
                    Object other$records = other.getRecords();
                    if (this$records == null) {
                        if (other$records == null) {
                            break label35;
                        }
                    } else if (this$records.equals(other$records)) {
                        break label35;
                    }

                    return false;
                }

                if (this.getTotal() != other.getTotal()) {
                    return false;
                } else if (this.getSize() != other.getSize()) {
                    return false;
                } else {
                    return this.getCurrent() == other.getCurrent();
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageVo;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $records = this.getRecords();
        result = result * 59 + ($records == null ? 43 : $records.hashCode());
        long $total = this.getTotal();
        result = result * 59 + Long.hashCode($total);
        long $size = this.getSize();
        result = result * 59 + Long.hashCode($size);
        long $current = this.getCurrent();
        result = result * 59 + Long.hashCode($current);
        return result;
    }

    public String toString() {
        return "PageVo(records=" + this.getRecords() + ", total=" + this.getTotal() + ", size=" + this.getSize() + ", current=" + this.getCurrent() + ")";
    }

    public static class PageVoBuilder<T> {
        private List<T> records;
        private long total;
        private long size;
        private long current;

        PageVoBuilder() {
        }

        public PageVoBuilder<T> records(final List<T> records) {
            this.records = records;
            return this;
        }

        public PageVoBuilder<T> total(final long total) {
            this.total = total;
            return this;
        }

        public PageVoBuilder<T> size(final long size) {
            this.size = size;
            return this;
        }

        public PageVoBuilder<T> current(final long current) {
            this.current = current;
            return this;
        }

        public PageVo<T> build() {
            return new PageVo(this.records, this.total, this.size, this.current);
        }

        public String toString() {
            return "PageVo.PageVoBuilder(records=" + this.records + ", total=" + this.total + ", size=" + this.size + ", current=" + this.current + ")";
        }
    }
}
