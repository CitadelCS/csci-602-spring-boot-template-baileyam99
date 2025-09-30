package edu.citadel.api.request;

import lombok.Data;

@Data
public class AllAccountRequestBody {
    private Integer currentPage;

    private Integer itemsPerPage;
}
