package com.example.demo.config;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Data
public class CustomPageableConfiguration {

        private Integer page;
        private Integer size;
        private String sort;
        private String sortBy;

        public static Pageable convertToPageable(CustomPageableConfiguration customPageableConfiguration) {
            if (customPageableConfiguration != null) {
                int page;
                if (customPageableConfiguration.getPage() != null) {
                    if (customPageableConfiguration.getPage() == 0) {
                        page = 0;
                    } else {
                        page = customPageableConfiguration.getPage() - 1;
                    }
                } else {
                    page = 0;
                }

                int size;
                if (customPageableConfiguration.getSize() != null) {
                    size = customPageableConfiguration.getSize();
                } else {
                    size = 20;
                }

                if(customPageableConfiguration.getSortBy() != null) {
                    if(customPageableConfiguration.getSort().toLowerCase().equals("asc")){
                        return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, customPageableConfiguration.getSortBy()));
                    }

                    return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, customPageableConfiguration.getSortBy() ));
                }

                return PageRequest.of(page, size);
            } else {
                if(customPageableConfiguration.getSortBy() != null) {
                    if(customPageableConfiguration.getSort().toLowerCase().equals("asc")){
                        return PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, customPageableConfiguration.getSortBy()));
                    }

                    return PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, customPageableConfiguration.getSortBy() ));
                }
                return PageRequest.of(0, 20);
            }
        }
    
}
