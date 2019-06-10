package kosiorek.michal.service;

import kosiorek.michal.exceptions.ExceptionCode;
import kosiorek.michal.exceptions.MyException;
import kosiorek.michal.model.Customer;
import kosiorek.michal.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerService {

    private CustomerRepository customerRepository = new CustomerRepository();

    public void addCustomer(Customer customer) {

        customerRepository.add(customer);

    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void editCustomer(Customer customer) {
        customerRepository.update(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer.getId());
    }

    public Customer getCustomerByNameSurnameEmail(String name, String surname, String email) {

        Optional<Customer> optionalCustomer = customerRepository.findByNameSurnameEmail(name, surname, email);

        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        }
        throw new MyException(ExceptionCode.OTHER, "Customer is null = not found");
    }

    public boolean hasLoyaltyCard(Customer customer) {
        if (customer.getLoyaltyCardId() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void editCustomerWithLoyaltyCard(Customer customer) {
        customerRepository.updateWithLoyaltyCard(customer);
    }

}
