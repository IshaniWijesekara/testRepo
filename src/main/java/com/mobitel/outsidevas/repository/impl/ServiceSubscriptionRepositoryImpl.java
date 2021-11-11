package com.mobitel.outsidevas.repository.impl;
import com.mobitel.outsidevas.config.IfxDBConfig;
import com.mobitel.outsidevas.model.ServiceSubscription;
import com.mobitel.outsidevas.repository.ServiceSubscriptionRepository;
import com.mobitel.outsidevas.util.exception.DataFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Repository
public class ServiceSubscriptionRepositoryImpl implements ServiceSubscriptionRepository {

    @Autowired
    @Qualifier("ifxJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public ServiceSubscriptionRepositoryImpl(){
        this.jdbcTemplate = new JdbcTemplate();
    }

    @Override
    public String validateUser(String vendorCode) throws DataFetchException {
        String storeCode = jdbcTemplate.queryForObject("select comp_code from webdata:mobserv_config where comp_code =? and para_name ='method_name' and para_value='VasSubscription'",new Object[]{vendorCode},String.class);
        if (storeCode != null) {
            return storeCode;
        }else {
            return null;
        }
    }

    @Override
    public String validateSubscribedUser(ServiceSubscription serviceSubscription) throws DataFetchException {
        List<String> list = new ArrayList<>();
        try {
            String sql = "select mobile_no from gsmcpsm:outside_vas_log where mobile_no=? and service_name=? and terminated_on is null";
            list = jdbcTemplate.query(sql, new Object[]{serviceSubscription.getMobileNo(),serviceSubscription.getServiceName()},new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    String mobile = resultSet.getString("mobile_no");
                    return mobile;
                }
            });
        } catch (Exception ex) {
            throw new DataFetchException(ex.getMessage(), ex);
        }
        if (list != null && list.size() >0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int saveVasLogDetails(ServiceSubscription serviceSubscription) throws DataFetchException {
        int index = 0;

        try {
            String sql = "insert into gsmcpsm:outside_vas_log(mobile_no, service_name, activated_on, act_update_on, activated_by)values (?,?,?,?,?)";
            index = jdbcTemplate.update(sql, new Object[]{serviceSubscription.getMobileNo(),serviceSubscription.getServiceName(),serviceSubscription.getActivatedDate(),new Date(),serviceSubscription.getVendorName()});
        } catch (Exception ex) {
            throw new DataFetchException(ex.getMessage(), ex);
        }
        return index;
    }

    @Override
    public int updateUnsubscribedDetails(ServiceSubscription serviceSubscription) throws DataFetchException {
        int index =0;
        try{
            index = jdbcTemplate.update("update gsmcpsm:outside_vas_log set terminated_on=?, term_update_on=?, terminated_by=? where mobile_no=? and service_name=? and terminated_on is null",new Object[]{
                    serviceSubscription.getDeActivatedDate(),new Date(),serviceSubscription.getVendorName(),serviceSubscription.getMobileNo(),serviceSubscription.getServiceName()});
        }catch (Exception ex) {
            throw new DataFetchException(ex.getMessage(), ex);
        }
        return index;
    }

    @Override
    public int saveUnSubscribedDetails(ServiceSubscription serviceSubscription) throws DataFetchException {
        int index;
        try {
            String sql = "insert into gsmcpsm:outside_vas_log(mobile_no, service_name, activated_on,act_update_on, activated_by, terminated_on, term_update_on, terminated_by)values (?,?,?,?,?,?,?,?)";
            index = jdbcTemplate.update(sql, new Object[]{serviceSubscription.getMobileNo(),
                    serviceSubscription.getServiceName(),
                    serviceSubscription.getActivatedDate(),
                    new Date(),
                    serviceSubscription.getVendorName(),
                    serviceSubscription.getDeActivatedDate(),
                    new Date(),
                    serviceSubscription.getVendorName()});
        } catch (Exception ex) {
            throw new DataFetchException(ex.getMessage(), ex);
        }
        return index;
    }
}
