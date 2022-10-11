package com.ksg.api.mapper;

import java.util.List;

import com.ksg.api.model.Code;
import com.ksg.api.model.CodeClass;

public class CodeMapper extends AbstractMapper{

    public CodeMapper()
    {
        this.namespace="codeMapper";
    }

    public List<CodeClass> selectAll(CodeClass param)
    {
        return selectList("selectAll", param);
    }
    public List<Code> selectDetailAll(Code param)
    {
        return selectList("selectDetailAll", param);
    }

    public List<CodeClass> selectByCondition(CodeClass param)
    {
        return selectList("selectByCondition", param);
    }

    public List<Code> selectDetailByCondition(Code param)
    {
        return selectList("selectDetailByCondition", param);
    }

    public CodeClass selectById(String code_class_id) {
        return (CodeClass) selectOne("selectById", code_class_id);
    }

    public Code selectDetailById(int code_id) {
        return (Code) selectOne("selectDetailById", code_id);
    }

    public CodeClass selectByKey(String code_class_name) {
        return (CodeClass) selectOne("selectByKey", code_class_name);
    }

    public Code selectDetailByKey(String code_class_id, String code_name) {

        Code code = Code.builder().code_class_id(code_class_id).code_name(code_name).build();
        return (Code) selectOne("selectDetailByKey", code);
    }

    public int insertCode(CodeClass param) throws Exception {
        return insert("insertCode", param);
    }

    public int insertCodeDetail(Code param) throws Exception{
        return insert("insertCodeDetail", param);
    }


    public int deleteCode(String code_class_id) {
        return delete("deleteCode",code_class_id);
    }

    public int deleteCodeDetail(int id) {
        return delete("deleteCodeDetail",id);
    }

    public int updateDetail(Code param) {
        return delete("updateCodeDetail",param);
    } 




    
}
