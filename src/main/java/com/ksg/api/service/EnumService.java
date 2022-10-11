package com.ksg.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Isolation;
// import org.springframework.transaction.annotation.Propagation;
// import org.springframework.transaction.annotation.Transactional;

// import kr.co.ldcc.platform.iws.admin.api.mapper.EnumMapper;
// import kr.co.ldcc.platform.iws.admin.api.mapper.ServiceMapper;
// import kr.co.ldcc.platform.iws.admin.api.model.Enum;
// import kr.co.ldcc.platform.iws.admin.api.model.EnumCodeValuePair;
// import kr.co.ldcc.platform.iws.admin.api.model.Enum_HIS;
// import kr.co.ldcc.platform.iws.admin.common.exception.AlreadyExistException;
// import kr.co.ldcc.platform.iws.admin.common.exception.RelationExistException;
// import kr.co.ldcc.platform.iws.admin.common.exception.RelationNotExistException;
// import kr.co.ldcc.platform.iws.admin.common.exception.ResourceNotFoundException;
// import kr.co.ldcc.platform.iws.admin.common.utility.HistoryUtility;
// import kr.co.ldcc.platform.iws.admin.common.utility.PlatformRequest;
// import kr.co.ldcc.platform.iws.admin.common.utility.QueryUtility;

@Service
public class EnumService {
    // private HistoryUtility historyUtility;
    // private QueryUtility queryUtility;

    // private ServiceMapper serviceMapper;
    // private EnumMapper enumMapper;

    // @Autowired
    // public EnumService (HistoryUtility historyUtility, QueryUtility queryUtility,
    //     EnumMapper enumMapper, ServiceMapper serviceMapper) {

    //     this.historyUtility = historyUtility;
    //     this.queryUtility = queryUtility;

    //     this.enumMapper = enumMapper;
    //     this.serviceMapper = serviceMapper;
    // }

    // @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, timeout = 30)
    // private int writeHistory (Enum enumInfo) {
    //     if (enumInfo == null) {
    //         return 0;
    //     }

    //     var queryParam = Enum_HIS.builder()
    //                         .serviceCode(enumInfo.getServiceCode())
    //                         .enumCode(enumInfo.getEnumCode())
    //                         .data(historyUtility.toJsonString(enumInfo))
    //                         .isDeleted(enumInfo.getIsDeleted())
    //                         .eventName(enumInfo.getEventName())
    //                         .eventUser(enumInfo.getEventUser())
    //                         .eventDate(enumInfo.getEventDate())
    //                         .eventTimekey(enumInfo.getEventTimekey())
    //                         .build();

    //     return enumMapper.createHistory(queryParam);
    // }

    // @Transactional(readOnly = true)
    // public Enum selectByKey (String serviceCode, String enumCode) {
    //     return enumMapper.selectByKey(serviceCode, enumCode);
    // }

    // @Transactional(readOnly = true)
    // public Enum selectDeletedByKey (String serviceCode, String enumCode) {
    //     return enumMapper.selectDeletedByKey(serviceCode, enumCode);
    // }

    // @Transactional(readOnly = true)
    // public List<Enum> select (Enum inputEnum) {
    //     var enumOption = Enum.builder()
    //                         .serviceCode(inputEnum.getServiceCode())
    //                         .enumCode(inputEnum.getEnumCode())
    //                         .enumName(queryUtility.getLikeString(inputEnum.getEnumName()))
    //                         .enumParentCode(inputEnum.getEnumParentCode())
    //                         .enumSeq(inputEnum.getEnumSeq())
    //                         .enumValue(inputEnum.getEnumValue())
    //                         .build();

    //     return enumMapper.select(enumOption)
    //                 .stream()
    //                 .sorted()
    //                 .collect(Collectors.toList());
    // }

    // @Transactional(readOnly = true)
    // public List<Enum> selectSub (String serviceCode, String parentEnumCode) {
    //     List<Enum> resultList = new ArrayList<>();

    //     var parentEnum = selectByKey(serviceCode, parentEnumCode);

    //     if (parentEnum == null) {
    //         return resultList;
    //     }

    //     var enumOption = Enum.builder()
    //                         .serviceCode(serviceCode)
    //                         .build();

    //     var enumList = select(enumOption);
    //     Stack<Enum> stack = new Stack<>();

    //     parentEnum.setEnumLevel(-1);
    //     stack.push(parentEnum);

    //     while (!stack.isEmpty()) {
    //         var targetEnum = stack.pop();

    //         if (targetEnum.getEnumLevel() > -1) {
    //             resultList.add(targetEnum);
    //         }

    //         enumList.stream()
    //             .filter(enumInfo -> enumInfo.getEnumParentCode() != null && enumInfo.getEnumParentCode().equals(targetEnum.getEnumCode()))
    //             .sorted()
    //             .forEach(enumInfo -> {
    //                 enumInfo.setEnumLevel(targetEnum.getEnumLevel() + 1);
    //                 stack.add(enumInfo);
    //             });
    //     }

    //     return resultList;
    // }

    // @Transactional(readOnly = true)
    // public List<Enum> selectLeaf (String serviceCode, String parentEnumCode) {
    //     List<Enum> resultList = new ArrayList<>();

    //     var parentEnum = selectByKey(serviceCode, parentEnumCode);

    //     if (parentEnum == null) {
    //         return resultList;
    //     }

    //     var enumOption = Enum.builder()
    //                         .serviceCode(serviceCode)
    //                         .build();

    //     var enumList = select(enumOption);
    //     Stack<Enum> stack = new Stack<>();

    //     parentEnum.setEnumLevel(-1);
    //     stack.push(parentEnum);

    //     while (!stack.isEmpty()) {
    //         var targetEnum = stack.pop();

    //         var childList = enumList.stream()
    //                             .filter(enumInfo -> enumInfo.getEnumParentCode() != null && enumInfo.getEnumParentCode().equals(targetEnum.getEnumCode()))
    //                             .sorted()
    //                             .collect(Collectors.toList());

    //         // internal middle node
    //         if (childList.size() > 0) {
    //             childList.forEach(enumInfo -> {
    //                 enumInfo.setEnumLevel(targetEnum.getEnumLevel() + 1);
    //                 stack.add(enumInfo);
    //             });
    //         }

    //         // leaf
    //         else {
    //             if (targetEnum.getEnumLevel() > -1) {
    //                 resultList.add(targetEnum);
    //             }
    //         }
    //     }

    //     return resultList;
    // }

    // @Transactional(readOnly = true)
    // public List<EnumCodeValuePair> selectName (String serviceCode, List<String> enumCodeList) {
    //     return enumMapper.selectName(serviceCode, enumCodeList);
    // }

    // @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, timeout = 30)
    // public Enum create (PlatformRequest platformRequest, Enum inputEnum) {
    //     var targetEnum = enumMapper.selectByKey(inputEnum.getServiceCode(), inputEnum.getEnumCode());

    //     if (targetEnum != null) {
    //         throw new AlreadyExistException();
    //     }

    //     // check service
    //     var relatedService = serviceMapper.selectByKey(inputEnum.getServiceCode());

    //     if (relatedService == null) {
    //         throw new RelationNotExistException();
    //     }

    //     // check parent enum code
    //     var enumOption = Enum.builder()
    //                 .serviceCode(inputEnum.getServiceCode())
    //                 .enumCode(inputEnum.getEnumParentCode())
    //                 .build();

    //     var parentEnum = select(enumOption);

    //     if (parentEnum == null || parentEnum.size() == 0) {
    //         throw new RelationNotExistException();
    //     }

    //     // create enum
    //     var enumCreateOption = Enum.builder()
    //                             .serviceCode(inputEnum.getServiceCode())
    //                             .enumCode(inputEnum.getEnumCode())
    //                             .enumName(inputEnum.getEnumName())
    //                             .enumParentCode(inputEnum.getEnumParentCode())
    //                             .enumSeq(inputEnum.getEnumSeq())
    //                             .enumValue(inputEnum.getEnumValue())
    //                             .isDeleted("N")
    //                             .description(inputEnum.getDescription())
    //                             .eventName(platformRequest.getEventName())
    //                             .eventUser(platformRequest.getEventUser())
    //                             .build();

    //     int resultCount = 0;
    //     targetEnum = enumMapper.selectDeletedByKey(enumCreateOption.getServiceCode(), enumCreateOption.getEnumCode());

    //     if (targetEnum != null) {
    //         resultCount = enumMapper.updateDeleted(enumCreateOption);
    //     }

    //     else {
    //         resultCount = enumMapper.create(enumCreateOption);
    //     }

    //     var result = (resultCount > 0) ? enumMapper.selectByKey(enumCreateOption.getServiceCode(), enumCreateOption.getEnumCode()) : null;
    //     writeHistory(result);

    //     return result;
    // }

    // @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, timeout = 30)
    // public Enum update (PlatformRequest platformRequest, Enum inputEnum) {
    //     var targetEnum = enumMapper.selectByKey(inputEnum.getServiceCode(), inputEnum.getEnumCode());

    //     if (targetEnum == null) {
    //         throw new ResourceNotFoundException();
    //     }

    //     var enumUpdateOption = Enum.builder()
    //                             .serviceCode(inputEnum.getServiceCode())
    //                             .enumCode(inputEnum.getEnumCode())
    //                             .enumName(queryUtility.getValue(inputEnum.getEnumName(), targetEnum.getEnumName()))
    //                             .enumParentCode(queryUtility.getValue(inputEnum.getEnumParentCode(), targetEnum.getEnumParentCode()))
    //                             .enumSeq(inputEnum.getEnumSeq())
    //                             .enumValue(inputEnum.getEnumValue())
    //                             .isDeleted("N")
    //                             .description(queryUtility.getValue(inputEnum.getDescription(), targetEnum.getDescription()))
    //                             .eventName(platformRequest.getEventName())
    //                             .eventUser(platformRequest.getEventUser())
    //                             .build();

    //     int resultCount = enumMapper.update(enumUpdateOption);
    //     var result = (resultCount > 0) ? enumMapper.selectByKey(enumUpdateOption.getServiceCode(), enumUpdateOption.getEnumCode()) : null;
    //     writeHistory(result);

    //     return result;
    // }

    // @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, timeout = 30)
    // public Enum delete (PlatformRequest platformRequest, Enum inputEnum) {
    //     var targetEnum = enumMapper.selectByKey(inputEnum.getServiceCode(), inputEnum.getEnumCode());

    //     if (targetEnum == null) {
    //         throw new ResourceNotFoundException();
    //     }

    //     var enumOption = Enum.builder()
    //                         .serviceCode(inputEnum.getServiceCode())
    //                         .enumParentCode(inputEnum.getEnumCode())
    //                         .build();

    //     var childCount = select(enumOption).stream()
    //                         .count();

    //     if (childCount > 0) {
    //         throw new RelationExistException();
    //     }

    //     var enumDeleteOption = Enum.builder()
    //                             .serviceCode(inputEnum.getServiceCode())
    //                             .enumCode(inputEnum.getEnumCode())
    //                             .isDeleted("Y")
    //                             .description(inputEnum.getDescription())
    //                             .eventName(platformRequest.getEventName())
    //                             .eventUser(platformRequest.getEventUser())
    //                             .build();

    //     int resultCount = enumMapper.delete(enumDeleteOption);
    //     var result = (resultCount > 0) ? enumMapper.selectDeletedByKey(enumDeleteOption.getServiceCode(), enumDeleteOption.getEnumCode()) : null;
    //     writeHistory(result);

    //     return result;
    // }
}