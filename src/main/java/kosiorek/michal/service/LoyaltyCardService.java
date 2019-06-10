package kosiorek.michal.service;

import kosiorek.michal.model.Customer;
import kosiorek.michal.model.LoyaltyCard;
import kosiorek.michal.repository.LoyaltyCardRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class LoyaltyCardService {

    private static final BigDecimal DISCOUNT = BigDecimal.valueOf(0.1);

    private LoyaltyCardRepository loyaltyCardRepository = new LoyaltyCardRepository();
    private CustomerService customerService = new CustomerService();

    public int addLoyaltyCardAndReturnId(LoyaltyCard loyaltyCard){
        return loyaltyCardRepository.addAndReturnId(loyaltyCard);
    }

    public LoyaltyCard getLoyaltyCardById(int id){
        Optional<LoyaltyCard> loyaltyCardOptional = loyaltyCardRepository.findById(id);
       return loyaltyCardOptional.orElse(new LoyaltyCard());
    }

    public void editLoyaltyCard(LoyaltyCard loyaltyCard){
        loyaltyCardRepository.update(loyaltyCard);
    }

    public void addLoyaltyCardToCustomer(Customer customer){
        LoyaltyCard loyaltyCard = LoyaltyCard.builder().moviesNumber(0).discount(DISCOUNT).expirationDate(LocalDate.now().plusYears(1)).build();
        int id = addLoyaltyCardAndReturnId(loyaltyCard);
        customer.setLoyaltyCardId(id);
        customerService.editCustomerWithLoyaltyCard(customer);
    }



}
