package com.mobitel.outsidevas.service.impl;

import com.mobitel.outsidevas.model.Response;
import com.mobitel.outsidevas.model.ServiceSubscription;
import com.mobitel.outsidevas.repository.impl.ServiceSubscriptionRepositoryImpl;
import com.mobitel.outsidevas.service.SubscribeService;
import com.mobitel.outsidevas.util.AppConstant;
import com.mobitel.outsidevas.util.exception.DataFetchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import java.sql.CallableStatement;
import java.sql.Connection;

@WebService(endpointInterface = "com.mobitel.outsidevas.service.SubscribeService")
@Component
public class SubscribeServiceImpl implements SubscribeService {
    private static final Logger INPUT_LOGGER = LoggerFactory.getLogger("inputLogger");
    private static final Logger OUTPUT_LOGGER = LoggerFactory.getLogger("outputLogger");

    @Autowired
    private ServiceSubscriptionRepositoryImpl serviceSubscriptionRepository;

    @Autowired
    @Qualifier("oracleEntityManager")
    EntityManager entityManager;

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Response vasSubscription(ServiceSubscription serviceSubscription) {
        Response res = new Response();

        if (serviceSubscription != null) {
            try {
                INPUT_LOGGER.info(serviceSubscription.toString());

                boolean validVasInputs = this.validVasInputs(serviceSubscription);
                if (validVasInputs == true){

                    String validateUser = this.validateUser(serviceSubscription.getVendorCode());
                    if (validateUser != null && !validateUser.equals("")) {

                        if(serviceSubscription.getRequstType().getRequestType().equals("SUBSCRIBED")){
                            int index = this.subscribedUser(serviceSubscription);
                            if(index >0){
                                res.setResponseDesc("Successfully updated");
                                res.setResponseCode(AppConstant.SUCCESS);
                                OUTPUT_LOGGER.info(res.toString());
                            }
                        }else if(serviceSubscription.getRequstType().getRequestType().equals("UNSUBSCRIBED")) {
                            int user = this.unSubscribedUser(serviceSubscription);
                            if (user == 0) {
                                int saveUnsubscribedUser = this.saveUnsubscribedUser(serviceSubscription);
                                if (saveUnsubscribedUser >0){
//                                    StoredProcedureQuery proc = entityManager.createStoredProcedureQuery(
//                                            "CALL VAS_DEACTIVATION");
//                                    proc.setParameteser("mobileNo",serviceSubscription.getMobileNo());
//                                    proc.execute();

                                    Connection connection = jdbcTemplate.getDataSource().getConnection();
                                    CallableStatement callableStatement = connection.prepareCall("{CALL VAS_DEACTIVATION(?, ?, ?,?,?)}");
                                    callableStatement.setString(1, serviceSubscription.getMobileNo());
                                    callableStatement.setString(2, " VendorDEACT");
                                    callableStatement.setString(3, "OYIM");
                                    callableStatement.setString(4, "0000");
                                    callableStatement.setString(5, "Error");
                                    callableStatement.execute();

                                    res.setResponseDesc("Successfully updated");
                                    res.setResponseCode(AppConstant.SUCCESS);
                                    OUTPUT_LOGGER.info(res.toString());
                                }
                            }else {
                                res.setResponseDesc("Active Already");
                                res.setResponseCode(AppConstant.ACTIVE_ALREADY);
                                OUTPUT_LOGGER.info(res.toString());
                            }
                        }
                    }else {
                        res.setResponseDesc("Invalid Vendor");
                        res.setResponseCode(AppConstant.INVALID_VENDOR);
                        OUTPUT_LOGGER.info(res.toString());
                    }

                }else {
                    res.setResponseDesc("Invalid parameter");
                    res.setResponseCode(AppConstant.INVALID_PARAMETER);
                    OUTPUT_LOGGER.info(res.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            res.setResponseDesc("Invalid parameter");
            res.setResponseCode(AppConstant.INVALID_PARAMETER);
            OUTPUT_LOGGER.info(res.toString());
        }
        return res;
    }

    public boolean validVasInputs(ServiceSubscription serviceSubscription) throws DataFetchException {
        boolean out = false;
        if(serviceSubscription.getMobileNo()!=null && serviceSubscription.getMobileNo().startsWith("07") && serviceSubscription.getMobileNo().length()==10 && serviceSubscription.getVendorCode()!=null && serviceSubscription.getServiceName()!=null && serviceSubscription.getActivatedDate()!=null){
            if(serviceSubscription.getRequstType().getRequestType().equals("SUBSCRIBED")){
                out = true;
            }else if(serviceSubscription.getRequstType().getRequestType().equals("UNSUBSCRIBED") && serviceSubscription.getDeActivatedDate()!=null){
                out = true;
            }
        }
        return out;
    }

    public String validateUser(String vendorName) throws Exception {
        if (vendorName != null && !vendorName.equals("")){
            String vendor = this.serviceSubscriptionRepository.validateUser(vendorName);
            return vendor;
        }else {
            return null;
        }
    }

    public int subscribedUser(ServiceSubscription serviceSubscription)throws DataFetchException{
        int index = 0;
        if (serviceSubscription != null && !serviceSubscription.equals("")) {
            String validateSubscribedUser = this.serviceSubscriptionRepository.validateSubscribedUser(serviceSubscription);
            if (validateSubscribedUser != null && !validateSubscribedUser.equals("")) {

                index = this.serviceSubscriptionRepository.saveVasLogDetails(serviceSubscription);
                if (index >0) {
                    return index;
                }
            }else {
                return index;
            }
        }
        return index;
    }

    public int unSubscribedUser(ServiceSubscription serviceSubscription)throws DataFetchException{
        int index = 0;
        if (serviceSubscription != null && !serviceSubscription.equals("")){
            index = this.serviceSubscriptionRepository.updateUnsubscribedDetails(serviceSubscription);
            return index;
        }
        return index;
    }

    public int saveUnsubscribedUser(ServiceSubscription serviceSubscription)throws DataFetchException{
        int index = 0;
        index = this.serviceSubscriptionRepository.saveUnSubscribedDetails(serviceSubscription);
        if (index >0) {
            return index;
        }
        return index;
    }
}
