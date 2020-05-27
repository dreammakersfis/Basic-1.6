package com.example.basicpay.data;

import android.annotation.SuppressLint;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ISOFormatter {
    private static Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ISOMessage isoMsg;
    private String dout = "";

    public ISOFormatter(ISOMessage iso) {
        this.isoMsg = iso;
    }

    private void setField(FIELDS fld, String s) {
        l.log(Level.INFO, "set Field : " + fld.toString() + " " + s);
        String data = "";
        data = formField(fld, s);
        l.log(Level.INFO, "form Field result : " + data);
        if (!data.equals("")) {
            dout = dout + data;
            if (fld.getNo() < 65)
                setBit(isoMsg.getpbitmap(), fld.getNo());
            else if (fld.getNo() < 129) {
                if (isoMsg.getsbitmap() == null) {
                    isoMsg.setSbitmap(new byte[8]);
                    initBitmap(isoMsg.getsbitmap());
                }
                setBit(isoMsg.getsbitmap(), (fld.getNo() - 64));
            }
        }
    }

    private String formField(FIELDS flde, String value) {
        StringBuilder data;

        if (value == null) {
            if (flde.getFormat() == 0) {
                return "";
            }
            else if (flde.getFormat() == 2) {
                data = new StringBuilder("00");
                return data.toString();
            }
            else if (flde.getFormat() == 3) {
                data = new StringBuilder("000");
                return data.toString();
            }
        }
        else if ((flde.getLength() != value.length()) && flde.isFixed()) {
            l.log(Level.INFO, "Error Formatting : 1 : ");
            return "";
        }
        else if (flde.getLength() < value.length()) {
            l.log(Level.INFO, "Error Formatting : 2 : ");
            return "";
        }
        else if (!flde.isFixed()) {
            if (flde.getFormat() != 0) {
                data = new StringBuilder(String.valueOf(value.length()));
                while (data.length() < flde.getFormat()) {
                    data.insert(0, "0");
                }
                data.append(value);
                return data.toString();
            }
        }
        else {
            data = new StringBuilder(value);
            return data.toString();
        }
        return "";
    }

    private static void setBit(byte[] data, int pos) {
        pos = pos - 1;
        int posByte = pos/8;
        int posBit = pos%8;
        byte oldByte = data[posByte];
        oldByte = (byte) (((0xFF7F>>posBit) & oldByte) & 0x00FF);
        byte newByte = (byte) ((1 <<(8-(posBit+1))) | oldByte);
        data[posByte] = newByte;
    }

    @SuppressLint("SimpleDateFormat")
    private String getTranDT(int type) {
        SimpleDateFormat sdf = null;
        if (type == 1) {
            sdf = new SimpleDateFormat("MMddHHmmss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        } else if (type == 2) {
            sdf = new SimpleDateFormat("HHmmss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-6"));
        } else if (type == 3) {
            sdf = new SimpleDateFormat("MMdd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-6"));
        } else if (type == 4) {
            sdf = new SimpleDateFormat("yDDD");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-6"));
        }
// Give it to me in GMT time.
        return sdf != null ? sdf.format(new Date()) : null;
    }

    private void initBitmap(byte[] bitmap) {
        for (int i = 0; i < 8; i++) {
            bitmap[i] = 0x0;
        }
    }

    public void buildFinancial() {
        dout = "";
        isoMsg.setMsgcode("0200");
        initBitmap(isoMsg.getpbitmap());
        setField(FIELDS.F2_PAN, isoMsg.getTranPAN());
        setField(FIELDS.F3_ProcessCode, isoMsg.getProcCode());
        setField(FIELDS.F4_AmountTransaction, isoMsg.getTranAmount());
        setField(FIELDS.F7_TranDateTime, getTranDT(1));
        setField(FIELDS.F11_Trace_No, isoMsg.getTraceNo());
        setField(FIELDS.F12_LocalTime, getTranDT(2));
        setField(FIELDS.F13_LocalDate, getTranDT(3));
        setField(FIELDS.F14_ExpirationDate, isoMsg.getExpirydate());
        setField(FIELDS.F15_SettlementDate, getTranDT(3));
        setField(FIELDS.F18_MerchantType,  isoMsg.getmerchtype());
        setField(FIELDS.F22_EntryMode,  isoMsg.getEntrymode());
        setField(FIELDS.F25_POS_ConditionCode, isoMsg.getPOScondcode());
        setField(FIELDS.F32_AcqrInstIdCode, isoMsg.getAcqrInstId());
        setField(FIELDS.F37_RRN, isoMsg.getRetRefNo(getTranDT(4)));
        setField(FIELDS.F41_CA_TerminalID, isoMsg.getTerminalID());
        setField(FIELDS.F43_CardAcceptorInfo, isoMsg.getCardAcptInfo());
        setField(FIELDS.F49_Curr_Code_Tran, isoMsg.getCurrCdTran());
        setField(FIELDS.F58_National_Cond_Code, isoMsg.getNatCondCode());
       // setField(FIELDS.F109_Sender_Data, isoMsg.getSenderdata());
       // setField(FIELDS.F110_Receiver_Data, isoMsg.getReceiverdata());
    }

    public void loginRequest() {
        dout = "";
        isoMsg.setMsgcode("0800");
        initBitmap(isoMsg.getpbitmap());
        setField(FIELDS.F7_TranDateTime, getTranDT(1));
        setField(FIELDS.F11_Trace_No, isoMsg.getTraceNo());
        setField(FIELDS.F70_Net_Mgmt_Code, isoMsg.getNetMgmtCode());
        setField(FIELDS.F96_Msg_Sec_Code, isoMsg.getMsgSecCode());
    }

    public void loginReply() {
        dout = "";
        isoMsg.setMsgcode("0810");

        initBitmap(isoMsg.getpbitmap());
        setField(FIELDS.F7_TranDateTime, getTranDT(1));
        setField(FIELDS.F11_Trace_No, isoMsg.getTraceNo());
        setField(FIELDS.F39_ResponseCode, isoMsg.getRespCode());
        setField(FIELDS.F70_Net_Mgmt_Code, isoMsg.getNetMgmtCode());
    }

    public byte[] getBuffer() {
        l.log(Level.INFO, "pbitmap : " + Arrays.toString(isoMsg.getpbitmap()));
        l.log(Level.INFO, "sbitmap : " + Arrays.toString(isoMsg.getsbitmap()));
        byte[] buffer;
        int buflen = 4 + 8 + dout.length();
        if (isoMsg.getsbitmap() != null) {
            setBit(isoMsg.getpbitmap(), 1);
            buflen += 8;
        }
        l.log(Level.INFO, "ISO header : " + buflen);
        buffer = new byte[(buflen + 2)];

        byte[] mlen = ByteBuffer.allocate(4).putInt(buflen).array();
        buffer[0] = mlen[2];
        buffer[1] = mlen[3];
        int pos = 2;
        byte[] temp3 = isoMsg.getMsgcode().getBytes();
        for (byte b : temp3) {
            buffer[pos] = b;
            pos += 1;
        }
        byte[] temp1 = isoMsg.getpbitmap();
        for (byte b : temp1) {
            buffer[pos] = b;
            pos += 1;
        }

        if (isoMsg.getsbitmap() != null) {
            byte[] temp2 = isoMsg.getsbitmap();
            for (byte b : temp2) {
                buffer[pos] = b;
                pos += 1;
            }
        }

        byte[] temp4 = dout.getBytes();
        for (byte b : temp4) {
            buffer[pos] = b;
            pos += 1;
        }
        return buffer;
    }


}
