package com.mobitel.outsidevas.service;

import com.mobitel.outsidevas.model.Response;
import com.mobitel.outsidevas.model.ServiceSubscription;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SubscribeService {
    @WebMethod
    public Response vasSubscription(@WebParam(name = "serviceSubscription") ServiceSubscription serviceSubscription);
}
