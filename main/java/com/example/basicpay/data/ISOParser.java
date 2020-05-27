package com.example.basicpay.data;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ISOParser {

    private byte[] indata;
    private byte[] pbitmap = new byte[8];
    private byte[] sbitmap = new byte[8];
    private ISOMessage isoMsg;
    private int msglen = 0;
    private static Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ISOParser(byte[] data, int len) {
        this.indata = data;
        this.msglen = len;
        isoMsg = new ISOMessage(0);
    }

    public ISOMessage getIsoMsg() {
        return isoMsg;
    }

    public void unpack() {
        l.log(Level.INFO, "Data received : Parser : " + indata);

        isoMsg.setMsgcode(Arrays.toString(Arrays.copyOfRange(indata, 2, 5)));
        isoMsg.setPbitmap(Arrays.copyOfRange(indata, 6, 13));

        l.log(Level.INFO, "IN Msgcode : " + isoMsg.getMsgcode());
        l.log(Level.INFO, "IN pbitmap : " + Arrays.toString(pbitmap));

        int i, j;
        int start = 14;
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if(isBitSet(pbitmap[i], j)) {
                    start = parseFld((i+1)*(j+1), start);
                }
            }
        }
        if (sbitmap != null) {
            for (i = 0; i < 8; i++) {
                for (j = 0; j < 8; j++) {
                    if (isBitSet(pbitmap[i], j)) {
                        start = parseFld((i + 1) * (j + 1), start);
                    }
                }
            }
        }
    }

    private boolean isBitSet(byte n, int k)
    {
        return (n & (1 << (k - 1))) == 1;
    }

    private int parseFld(int de, int st) {
        int delen;
        if (de == 1) {
            isoMsg.setSbitmap(Arrays.copyOfRange(indata, st, st + 8));
            sbitmap = isoMsg.getsbitmap();
            return st + 8 + 1;
        }
        else {
            FIELDS fld = FIELDS.valueOf(de);
            if (fld.isFixed()) {
                setField(de,  Arrays.toString(Arrays.copyOfRange(indata, st, st + fld.getLength())));
                return st + fld.getLength() + 1;
            }
            else if ((fld.getFormat() == 2) || (fld.getFormat() == 3)) {
                delen = Integer.parseInt(Arrays.toString(Arrays.copyOfRange(indata, st, st + fld.getFormat())));
                if (delen > 0) {
                    setField(de, Arrays.toString(Arrays.copyOfRange(indata, st, st + delen)));
                    return st + delen + 1;
                }
                else {
                    return st + fld.getFormat() + 1;
                }
            }
        }
        return st;
    }

    private void setField(int de, String str) {
        if (de == FIELDS.F2_PAN.getNo()) {
            isoMsg.setTranPAN(str);
        }
        if (de == FIELDS.F3_ProcessCode.getNo()) {
            isoMsg.setProcCode(str);
        }
        if (de == 4) {
            isoMsg.setTranAmount(str);
        }
        else if (de == 7) {
            isoMsg.setTrandatetime(str);
        }
        else if (de == 11) {
            isoMsg.setTraceNo(str);
        }
        else if (de == 12) {
            isoMsg.setLocaltime(str);
        }
        else if (de == 13) {
            isoMsg.setLocaldate(str);
        }
        else if (de == 14) {
            isoMsg.setExpirydate(str);
        }
        else if (de == 15) {
            isoMsg.setSettdate(str);
        }
        else if (de == 18) {
            isoMsg.setmerchtype(str);
        }
        else if (de == 22) {
            isoMsg.setEntrymode(str);
        }
        else if (de == 25) {
            isoMsg.setPOScondcode(str);
        }
        else if (de == 37) {
            isoMsg.setRetRefNo(str);
        }
        else if (de == 39) {
            isoMsg.setRespCode(str);
        }
        else if (de == 41) {
            isoMsg.setTerminalID(str);
        }
        else if (de == 43) {
            isoMsg.setCardAcptInfo(str);
        }
        else if (de == 49) {
            isoMsg.setCurrCdTran(str);
        }
        else if (de == 58) {
            isoMsg.setNatCondCode(str);
        }
        else if (de == 70) {
            isoMsg.setNetMgmtCode(str);
        }
        else if (de == 96) {
            isoMsg.setMsgSecCode(str);
        }
        else if (de == 109) {
            isoMsg.setSenderdata(str);
        }
        else if (de == 110) {
            isoMsg.setReceiverdata(str);
        }
    }
}
