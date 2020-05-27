package com.example.basicpay.data;

public class ISOMessage {

    private byte[] msglen = new byte[2];
    private String msgcode = "0000";
    private byte[] pbitmap = new byte[8];
    private byte[] sbitmap;
    private String tranPAN = "4733368312898010";
    private String proccode = "540000";
    private String tranAmount = "000000002000";
    private String trandatetime = "";
    private String traceNo = "000001";
    private String localtime = "";
    private String localdate = "";
    private String expirydate = "4912";
    private String settdate = "";
    private String merchtype = "5211";
    private String entrymode = "810";
    private String POScondcode = "00";
    private String acqrInstId = "1000000518";
    private String retRefNo = "";
    private String respCode = "00";
    private String terminalID = "TERM4141";
    private String cardAcptInfo = "ADDRESS                CITY         WIUS";
    private String currCdTran = "840";
    private String natCondCode = "0100000001";
    private String NetMgmtCode = "";
    private String msgSecCode = "COOP1   ";
    private String senderdata = "N111SASIKUMAR STL104140001234EM21SAMPLEEMAIL@GMAIL.COMDB0801011991A126LAKE PARK DRIVE, WISCONSINR1191111111111222222222";
    private String receiverdata = "N129WALMART STORES MENOMINE FALLSTL104140004567EM22WALMARTFALLS@GMAIL.COMA1204-81, MENOMINE FALLSR118222222222333333333A619WALMART54751@FISPAY";

    public ISOMessage(int traceNo) {
        setTraceNo(traceNo);
    }

    byte[] getMsgLen() {
        return this.msglen;
    }

    public void setMsgLen(byte[] msglen) {
        this.msglen = msglen;
    }

    public String getMsgcode() {
        return msgcode;
    }

    void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    void setPbitmap(byte[] pbitmap) {
        this.pbitmap = new byte[8];
        this.pbitmap = pbitmap;
    }

    byte[] getpbitmap() {
        return pbitmap;
    }

    void setSbitmap(byte[] sbitmap) {
        this.sbitmap = new byte[8];
        this.sbitmap = sbitmap;
    }

    byte[] getsbitmap() {
        return sbitmap;
    }

    void setTranPAN(String tranPAN) {
        this.tranPAN = tranPAN;
    }

    String getTranPAN() {
        return tranPAN;
    }

    String getProcCode() {
        return proccode;
    }

    void setProcCode(String proccode) {
        this.proccode = proccode;
    }

    String getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(String tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getTrandatetime() {
        return trandatetime;
    }

    void setTrandatetime(String trandatetime) {
        this.trandatetime = trandatetime;
    }

    String getTraceNo() {
        return traceNo;
    }

    public int getTraceNoInt() {
        return Integer.parseInt(this.traceNo);
    }

    private void setTraceNo(int traceNo) {
        StringBuilder trcno = new StringBuilder(String.valueOf(traceNo));
        while (trcno.length() < 6) {
            trcno.insert(0, "0");
        }
        this.traceNo = trcno.toString();
    }

    void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getLocaltime() {
        return localtime;
    }

    void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getLocaldate() {
        return localdate;
    }

    void setLocaldate(String localdate) {
        this.localdate = localdate;
    }


    String getExpirydate() {
        return expirydate;
    }

    void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getSettdate() {
        return settdate;
    }

    void setSettdate(String settdate) {
        this.settdate = settdate;
    }

    String getmerchtype() {
        return merchtype;
    }

    void setmerchtype(String merchtype) {
        this.merchtype = merchtype;
    }

    String getPOScondcode() {
        return POScondcode;
    }

    void setPOScondcode(String POScondcode) {
        this.POScondcode = POScondcode;
    }

    String getEntrymode() {
        return entrymode;
    }

    void setEntrymode(String entrymode) {
        this.entrymode = entrymode;
    }

    public void setAcqrInstId(String acqrInstId) {
        this.acqrInstId = acqrInstId;
    }

    String getAcqrInstId() {
        return acqrInstId;
    }

    String getNatCondCode() {
        return natCondCode;
    }

    void setNatCondCode(String natCondCode) {
        this.natCondCode = natCondCode;
    }

    String getCurrCdTran() {
        return currCdTran;
    }

    void setCurrCdTran(String currCdTran) {
        this.currCdTran = currCdTran;
    }

    String getCardAcptInfo() {
        return cardAcptInfo;
    }

    void setCardAcptInfo(String cardAcptInfo) {
        this.cardAcptInfo = cardAcptInfo;
    }

    String getRespCode() {
        return respCode;
    }

    void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    String getTerminalID() {
        return terminalID;
    }

    void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    String getMsgSecCode() {
        return msgSecCode;
    }

    void setMsgSecCode(String msgSecCode) {
        this.msgSecCode = msgSecCode;
    }

    String getNetMgmtCode() {
        if (msgcode.equals("0800")) {
            NetMgmtCode = "001";
        } else if (msgcode.equals("0810")) {
            NetMgmtCode = "061";
        }
        return NetMgmtCode;
    }

    void setNetMgmtCode(String netMgmtCode) {
        NetMgmtCode = netMgmtCode;
    }

    String getRetRefNo(String YDDD) {
        this.retRefNo = YDDD.substring(3) + "00" + getTraceNo();
        return this.retRefNo;
    }

    void setRetRefNo(String retRefNo) {
        this.retRefNo = retRefNo;
    }

    String getSenderdata() {
        return senderdata;
    }

    void setSenderdata(String senderdata) {
        this.senderdata = senderdata;
    }

    String getReceiverdata() {
        return receiverdata;
    }

    void setReceiverdata(String receiverdata) {
        this.receiverdata = receiverdata;
    }
}