package org.example.workbench.service.impl;

import org.example.commons.contants.ContansDemo;
import org.example.commons.utils.DateUtils;
import org.example.commons.utils.UUIDUtils;
import org.example.settings.domain.User;
import org.example.workbench.domain.Clue;
import org.example.workbench.domain.Customer;
import org.example.workbench.mapper.ClueMapper;
import org.example.workbench.mapper.CustomerMapper;
import org.example.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("clueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private CustomerMapper customerMapper;

    //保存创建的线索
    @Override
    public int saveClue(Clue clue) {

        return clueMapper.insertClue(clue);
    }
    //根据条件分页查询
    @Override
    public List<Clue> queryClueByConditionForPage(Map<String, Object> map) {

        return clueMapper.selectClueByConditionForPage(map);
    }
    //查询总条数
    @Override
    public int queryCountOfClueByConditionForPage(Map<String, Object> map) {

        return clueMapper.selectCountOfClueByConditionForPage(map);
    }
    //查询线索明细
    @Override
    public Clue queryClueForDetailById(String id) {

        return clueMapper.selectClueForDetailById(id);
    }

    /**
     * 处理线索转换，保存
     */
    @Override
    public void saveConvertClue(Map<String,Object> map) {
        //
        User user = (User) map.get(ContansDemo.SESSION_USER);
        String isCreateTran = (String) map.get("isCreateTran");
        //根据clueId查询线索
        String clueId = (String) map.get("clueId");
        Clue clue = clueMapper.selectClueForConvertByClueId(clueId);

        //把线索的相关信息保存到客户表中
        Customer customer = new Customer();
        customer.setId(UUIDUtils.getUUID());
        customer.setOwner(user.getId());
        customer.setName(clue.getCompany());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formateDateTime(new Date()));
        customer.setContactSummary(clue.getContactSummary());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setDescription(clue.getDescription());
        customer.setAddress(clue.getAddress());
        customerMapper.insertCustomerAboutClue(customer);

    }
}
