package com.mobitel.outsidevas.repository;

import com.mobitel.outsidevas.model.ServiceSubscription;
import com.mobitel.outsidevas.util.exception.DataFetchException;

public interface ServiceSubscriptionRepository {
    String validateUser(String vendorCode)throws DataFetchException;

    String validateSubscribedUser(ServiceSubscription serviceSubscription)throws DataFetchException;

    int saveVasLogDetails(ServiceSubscription serviceSubscription)throws DataFetchException;

    int updateUnsubscribedDetails(ServiceSubscription serviceSubscription)throws DataFetchException;

    int saveUnSubscribedDetails(ServiceSubscription serviceSubscription)throws DataFetchException;
}
