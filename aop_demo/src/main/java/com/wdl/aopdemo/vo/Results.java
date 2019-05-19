package com.wdl.aopdemo.vo;

import com.wdl.aopdemo.constant.BusinessResultCode;

public class Results {

    public static ResultVO success(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(BusinessResultCode.SUCCESS.getCode());
        resultVO.setMessage(BusinessResultCode.SUCCESS.getMessage());
        return resultVO;
    }
    public static ResultVO error(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(BusinessResultCode.ERROR.getCode());
        resultVO.setMessage(BusinessResultCode.ERROR.getMessage());
        return resultVO;
    }
    public static ResultVO businessError(BusinessResultCode businessResultCode){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(businessResultCode.getCode());
        resultVO.setMessage(businessResultCode.getMessage());
        return resultVO;
    }
}
