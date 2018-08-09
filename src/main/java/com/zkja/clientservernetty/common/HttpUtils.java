package com.zkja.clientservernetty.common;

import com.zkja.clientservernetty.domain.TcpReq;
import com.zkja.clientservernetty.domain.TcpRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zzr
 */
public class HttpUtils {
    final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static Map<String,String> getHttpReq(TcpRes tcpRes){
        if(SmuConstant.ACTION_LOGIN.equals(tcpRes.getBwlx())){
            //解析出数据发送到smc
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bwlx", SmuConstant.ACTION_LOGIN);
            paramsMap.put("bwlsh", tcpRes.getBwlsh());
            paramsMap.put("imei", tcpRes.getImei());
            paramsMap.put("pid", tcpRes.getPid());
            paramsMap.put("sjh", tcpRes.getSjh());
            paramsMap.put("yjlx",tcpRes.getYjlx());
            //String reqJson = GsonUtil.toJson(paramsMap);
            /*logger.info("requestLogin==="+reqJson);
            String respJson = HttpGetPostUtil.doPostStr(url, paramsMap);
            logger.info("responeLogin==="+respJson);*/
            return paramsMap;

        }else if(SmuConstant.ACTION_LOGOUT.equals(tcpRes.getBwlx())){
            //解析出数据发送到smc
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bwlx", SmuConstant.ACTION_LOGOUT);
            paramsMap.put("bwlsh", tcpRes.getBwlsh());
            paramsMap.put("imei", tcpRes.getImei());
            paramsMap.put("pid", tcpRes.getPid());
            paramsMap.put("sjh", tcpRes.getSjh());
            String reqJson = GsonUtil.toJson(paramsMap);

        }else if(SmuConstant.ACTION_HEARTBEAT.equals(tcpRes.getBwlx())){
            //解析出数据发送到smc
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bwlx", SmuConstant.ACTION_HEARTBEAT);
            paramsMap.put("bwlsh", tcpRes.getBwlsh());
            paramsMap.put("imei", tcpRes.getImei());
            paramsMap.put("pid", tcpRes.getPid());
            paramsMap.put("sjh", tcpRes.getSjh());
            //String reqJson = GsonUtil.toJson(paramsMap);
            return paramsMap;
        }else if(SmuConstant.ACTION_SMU_SETUP.equals(tcpRes.getBwlx())){
            //解析出数据发送到smc
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bwlx", SmuConstant.ACTION_SMU_SETUP);
            paramsMap.put("bwlsh", tcpRes.getBwlsh());
            paramsMap.put("imei", tcpRes.getImei());
            paramsMap.put("pid", tcpRes.getPid());
            paramsMap.put("sjh", tcpRes.getSjh());
            //String reqJson = GsonUtil.toJson(paramsMap);
            return paramsMap;
        }else if(SmuConstant.ACTION_REPORT.equals(tcpRes.getBwlx())){
            //解析出数据发送到smc
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bwlx", SmuConstant.ACTION_REPORT);
            paramsMap.put("bwlsh", tcpRes.getBwlsh());
            paramsMap.put("imei", tcpRes.getImei());
            paramsMap.put("pid", tcpRes.getPid());
            paramsMap.put("sjh", tcpRes.getSjh());
            paramsMap.put("qdlx", tcpRes.getQdlx());
            paramsMap.put("cbwlx", "GPRMC");
//						    	 String time = new SimpleDateFormat("yyyymmddHHMMSS").format(new Date());
            paramsMap.put("gpstime", tcpRes.getGpstime());
            paramsMap.put("av", tcpRes.getAv());
            paramsMap.put("la", tcpRes.getLa());
            paramsMap.put("wdbs", tcpRes.getWdbs());
            paramsMap.put("lo", tcpRes.getLo());
            paramsMap.put("jdbs", tcpRes.getJdbs());
            paramsMap.put("sd", tcpRes.getSd());
            paramsMap.put("fx", tcpRes.getFx());
            paramsMap.put("locid", tcpRes.getLocid());
            paramsMap.put("ccid", tcpRes.getCcid());
            paramsMap.put("sjdl", tcpRes.getSjdl());
            paramsMap.put("sbdl", tcpRes.getSbdl());
            paramsMap.put("dssclx", tcpRes.getDssclx());
            paramsMap.put("sjjg", tcpRes.getSjjg());
            paramsMap.put("jqlx", tcpRes.getJqlx());
            paramsMap.put("sbaz", tcpRes.getSbaz());
            paramsMap.put("sfjj", tcpRes.getSfjj());
            //String reqJson = GsonUtil.toJson(paramsMap);
            return paramsMap;
        }else if(SmuConstant.ACTION_REPORT_NEW.equals(tcpRes.getBwlx())){
            //解析出数据发送到smc
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bwlx", SmuConstant.ACTION_REPORT_NEW);
            paramsMap.put("bwlsh", tcpRes.getBwlsh());
            paramsMap.put("imei", tcpRes.getImei());
            paramsMap.put("pid", tcpRes.getPid());
            paramsMap.put("sjh", tcpRes.getSjh());
            paramsMap.put("qdlx", tcpRes.getQdlx());
            paramsMap.put("cbwlx", "GPRMC");
//						    	 String time = new SimpleDateFormat("yyyymmddHHMMSS").format(new Date());
            paramsMap.put("gpstime", tcpRes.getGpstime());
            paramsMap.put("av", tcpRes.getAv());
            paramsMap.put("la", tcpRes.getLa());
            paramsMap.put("wdbs", tcpRes.getWdbs());
            paramsMap.put("lo", tcpRes.getLo());
            paramsMap.put("jdbs", tcpRes.getJdbs());
            paramsMap.put("sd", tcpRes.getSd());
            paramsMap.put("fx", tcpRes.getFx());
            paramsMap.put("locid", tcpRes.getLocid());
            paramsMap.put("ccid", tcpRes.getCcid());
            paramsMap.put("sjdl", tcpRes.getSjdl());
            paramsMap.put("sbdl", tcpRes.getSbdl());
            paramsMap.put("dssclx", tcpRes.getDssclx());
            paramsMap.put("sjjg", tcpRes.getSjjg());
            paramsMap.put("jqlx", tcpRes.getJqlx());
            paramsMap.put("sbaz", tcpRes.getSbaz());
            paramsMap.put("sfjj", tcpRes.getSfjj());
            paramsMap.put("iccid", tcpRes.getIccid());
            paramsMap.put("jrfs", tcpRes.getJrfs());
            paramsMap.put("cbwsm", tcpRes.getCbwsm());
            paramsMap.put("yl", tcpRes.getYl());
            paramsMap.put("mobilenet", tcpRes.getMobilenet());
            paramsMap.put("wifi", tcpRes.getWifi());
            paramsMap.put("bt", tcpRes.getBt());
            paramsMap.put("terminalip", tcpRes.getTerminalip());
            paramsMap.put("reserved", tcpRes.getReserved());
            paramsMap.put("sourceip", tcpRes.getSourceIp());
            paramsMap.put("reserved_wd", tcpRes.getReserved_wd());
            paramsMap.put("reserved_xy", tcpRes.getReserved_xy());
            paramsMap.put("reserved_xl", tcpRes.getReserved_xl());
            paramsMap.put("reserved_jb", tcpRes.getReserved_jb());
            paramsMap.put("reserved_ryzt", tcpRes.getReserved_ryzt());
            paramsMap.put("reserved_xdgl", tcpRes.getReserved_xdgl());
            //String reqJson = GsonUtil.toJson(paramsMap);
            return paramsMap;
        }else if(SmuConstant.ACTION_REPORT_RUN.equals(tcpRes.getBwlx())){
            //解析出数据发送到smc
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bwlx", SmuConstant.ACTION_REPORT_RUN);
            paramsMap.put("bwlsh", tcpRes.getBwlsh());
            paramsMap.put("imei", tcpRes.getImei());
            paramsMap.put("pid", tcpRes.getPid());
            paramsMap.put("sjh", tcpRes.getSjh());
            paramsMap.put("sbwd", tcpRes.getSbwd());
            paramsMap.put("ryxy",tcpRes.getRyxy());
//						    	 String time = new SimpleDateFormat("yyyymmddHHMMSS").format(new Date());
            paramsMap.put("ryxl", tcpRes.getRyxl());
            paramsMap.put("xzbs", tcpRes.getXzbs());
            paramsMap.put("gl", tcpRes.getGl());
            paramsMap.put("jlcs", tcpRes.getJlcs());
            paramsMap.put("mostyxf", tcpRes.getMostyxf());
            paramsMap.put("jbz", tcpRes.getJbz());
            paramsMap.put("mopoyxf", tcpRes.getMopoyxf());
            paramsMap.put("yddw", tcpRes.getYddw());
            paramsMap.put("dycjlsj", tcpRes.getDycjlsj());
            paramsMap.put("dycjlrq", tcpRes.getDycjlrq());
            //String reqJson = GsonUtil.toJson(paramsMap);
            return paramsMap;
        }else if(SmuConstant.ACTION_WN.equals(tcpRes.getBwlx())){
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("bwlx", SmuConstant.ACTION_WN);
            paramsMap.put("bwlsh", tcpRes.getBwlsh());
            paramsMap.put("imei", tcpRes.getImei());
            paramsMap.put("pid", tcpRes.getPid());
            paramsMap.put("sjh", tcpRes.getSjh());
            paramsMap.put("nr",tcpRes.getNr());
            //String reqJson = GsonUtil.toJson(paramsMap);
            return paramsMap;
        }
        return null;
    }

    public static TcpReq getHttpRes(String respJson){
        logger.info("responeLogin==="+respJson);
        Map<String, String> map = GsonUtil.jsonToMap(respJson);
        TcpReq tcpReqSmu = new TcpReq();
        if(SmuConstant.ACTION_LOGIN_RES.equals(map.get("bwlx"))){
            tcpReqSmu.setBwlsh(map.get("bwlsh"));
            tcpReqSmu.setBwlx(map.get("bwlx"));
            tcpReqSmu.setDsscsj(map.get("dsscsj"));
            tcpReqSmu.setImei(map.get("imei"));
            tcpReqSmu.setPid(map.get("pid"));
            tcpReqSmu.setQdlx(map.get("qdlx"));
            tcpReqSmu.setSfjj(map.get("sfjj"));
            tcpReqSmu.setSjh(map.get("sjh"));
            tcpReqSmu.setSjtb(map.get("sjtb"));
            return tcpReqSmu;
        }else if(SmuConstant.ACTION_LOGOUT_RES.equals(map.get("bwlx"))){
            tcpReqSmu.setBwlsh(map.get("bwlsh"));
            tcpReqSmu.setBwlx(map.get("bwlx"));
            tcpReqSmu.setImei(map.get("imei"));
            tcpReqSmu.setPid(map.get("pid"));
            tcpReqSmu.setSjh(map.get("sjh"));
            return tcpReqSmu;
        }else if(SmuConstant.ACTION_HEARTBEAT_RES.equals(map.get("bwlx"))){
            tcpReqSmu.setBwlsh(map.get("bwlsh"));
            tcpReqSmu.setBwlx(map.get("bwlx"));
            tcpReqSmu.setImei(map.get("imei"));
            tcpReqSmu.setPid(map.get("pid"));
            tcpReqSmu.setSjh(map.get("sjh"));
            return tcpReqSmu;
        }else if(SmuConstant.ACTION_SMU_SETUP_RES.equals(map.get("bwlx"))){
            tcpReqSmu.setBwlsh(map.get("bwlsh"));
            tcpReqSmu.setBwlx(map.get("bwlx"));
            tcpReqSmu.setImei(map.get("imei"));
            tcpReqSmu.setPid(map.get("pid"));
            tcpReqSmu.setSjh(map.get("sjh"));
            return tcpReqSmu;
        }else if(SmuConstant.ACTION_SMU_F2_ANSWER.equals(map.get("bwlx"))){
            tcpReqSmu.setBwlsh(map.get("bwlsh"));
            tcpReqSmu.setBwlx(map.get("bwlx"));
            tcpReqSmu.setImei(map.get("imei"));
            tcpReqSmu.setPid(map.get("pid"));
            tcpReqSmu.setSjh(map.get("sjh"));
            return tcpReqSmu;
        }else if(SmuConstant.ACTION_SMU_F5_ANSWER.equals(map.get("bwlx"))){
            tcpReqSmu.setBwlsh(map.get("bwlsh"));
            tcpReqSmu.setBwlx(map.get("bwlx"));
            tcpReqSmu.setImei(map.get("imei"));
            tcpReqSmu.setPid(map.get("pid"));
            tcpReqSmu.setSjh(map.get("sjh"));
            return tcpReqSmu;
        }else if(SmuConstant.ACTION_SMU_F9_ANSWER.equals(map.get("bwlx"))){
            tcpReqSmu.setBwlsh(map.get("bwlsh"));
            tcpReqSmu.setBwlx(map.get("bwlx"));
            tcpReqSmu.setImei(map.get("imei"));
            tcpReqSmu.setPid(map.get("pid"));
            tcpReqSmu.setSjh(map.get("sjh"));
            return tcpReqSmu;
        }else if(SmuConstant.ACTION_SMU_WN_ANSWER.equals(map.get("bwlx"))){
            tcpReqSmu.setBwlsh(map.get("bwlsh"));
            tcpReqSmu.setBwlx(map.get("bwlx"));
            tcpReqSmu.setImei(map.get("imei"));
            tcpReqSmu.setPid(map.get("pid"));
            tcpReqSmu.setSjh(map.get("sjh"));
            return tcpReqSmu;
        }
        return null;
    }
}
