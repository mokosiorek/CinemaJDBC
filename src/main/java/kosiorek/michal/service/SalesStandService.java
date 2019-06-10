package kosiorek.michal.service;

import kosiorek.michal.model.Customer;
import kosiorek.michal.model.SalesStand;
import kosiorek.michal.repository.SalesStandRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SalesStandService {

    private SalesStandRepository salesStandRepository = new SalesStandRepository();

    public void buyTicket(SalesStand salesStand) {
        salesStandRepository.add(salesStand);
    }

    public long getNumberOfTicketsBoughtByCustomer(Customer customer) {
        return salesStandRepository.findAll().stream().filter(salesStand -> salesStand.getCustomerId()==customer.getId()).count();
    }

    public List<SalesStand> getTicketsBoughtByCustomer(Customer customer){
        return salesStandRepository.findAll().stream().filter(salesStand -> salesStand.getCustomerId()==customer.getId()).collect(Collectors.toList());
    }


}
