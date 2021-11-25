package org.mariangolea.fintrack.bank.parser.persistence.repository.transactions;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionService {

    @Autowired
    BankTransactionRepository transactionRepository;
    
    @Autowired
    BankTransactionTextRepository transactionTextRepository;

    public List<BankTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public void saveOrUpdate(BankTransaction transaction) {
        BankTransactionText original = transaction.getOriginalContent();
        if (original.getOriginalContent().length() > 255){
            original.setOriginalContent(original.getOriginalContent().substring(0,255));
        }
        transactionRepository.save(transaction);
    }
    
    public void saveOrUpdate(Collection<BankTransaction> transactions) {
        for (BankTransaction transaction : transactions){
            saveOrUpdate(transaction);
        }
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
