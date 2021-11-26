package org.mariangolea.fintrack.bank.parser.persistence.transactions;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionService {

    @Autowired
    BankTransactionRepository transactionRepository;
    
    public List<BankTransaction> getTransactions(Date startDate, Date endDate, int page, int pageSize){
    	return transactionRepository.findAllByCompletedBetween(startDate, endDate, PageRequest.of(page, pageSize));
    }
}
