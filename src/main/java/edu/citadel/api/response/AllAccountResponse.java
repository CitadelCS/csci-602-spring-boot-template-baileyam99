package edu.citadel.api.response;

import edu.citadel.dal.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllAccountResponse {
    private Integer currentPage;

    private Integer itemsPerPage;

    private Integer totalItems;

    private List<Account> accounts;
}
