package com.mobitel.outsidevas.util;

public class HeaderMapper {
    private static final ResponseHeader responseHeader;

    static {
        responseHeader = new ResponseHeader();
    }

    /**
     * SUCCESS header mapping.
     *
     * @param iCrmResponse
     * @param extraMessage
     */
    public static final void success(IGlobalResponse iCrmResponse, String extraMessage, boolean extraMessageOnly) {
        responseHeader.setResponseCode(ApplicationConstants.SUCCESS);
        if (extraMessageOnly) {
            responseHeader.setResponseDesc(extraMessage);
        } else {
            responseHeader.setResponseDesc(ApplicationConstants.SUCCESS_MSG + " " + extraMessageValidate(extraMessage));
        }
        iCrmResponse.setResponseHeader(responseHeader);
    }

    /**
     * SYSTEM_ERROR header mapping.
     *
     * @param iCrmResponse
     * @param extraMessage
     */
    public static final void systemError(IGlobalResponse iCrmResponse, String extraMessage, boolean extraMessageOnly) {
        responseHeader.setResponseCode(ApplicationConstants.SYSTEM_ERROR);
        if (extraMessageOnly) {
            responseHeader.setResponseDesc(extraMessage);
        } else {
            responseHeader.setResponseDesc(ApplicationConstants.SYSTEM_ERROR_MSG + " " + extraMessageValidate(extraMessage));
        }
        iCrmResponse.setResponseHeader(responseHeader);
    }

    /**
     * Extra message is validation.
     *
     * @param extraMessage
     * @return
     */
    private static String extraMessageValidate(String extraMessage) {
        return extraMessage == null ? "" : extraMessage;
    }

    /**
     * INVALID_VENDOR header mapping.
     *
     * @param iCrmResponse
     * @param extraMessage
     */
    public static final void invalidVendorError(IGlobalResponse iCrmResponse, String extraMessage, boolean extraMessageOnly) {
        responseHeader.setResponseCode(ApplicationConstants.INVALID_VENDOR);
        if (extraMessageOnly) {
            responseHeader.setResponseDesc(extraMessage);
        } else {
            responseHeader.setResponseDesc(ApplicationConstants.INVALID_VENDOR_MSG + " " + extraMessageValidate(extraMessage));
        }
        iCrmResponse.setResponseHeader(responseHeader);
    }

    /**
     * INVALID_PARAMETER header mapping.
     *
     * @param iCrmResponse
     * @param extraMessage
     */
    public static final void invalidParameterError(IGlobalResponse iCrmResponse, String extraMessage, boolean extraMessageOnly) {
        responseHeader.setResponseCode(ApplicationConstants.INVALID_PARAMETER);
        if (extraMessageOnly) {
            responseHeader.setResponseDesc(extraMessage);
        } else {
            responseHeader.setResponseDesc(ApplicationConstants.INVALID_PARAMETER_MSG + " " + extraMessageValidate(extraMessage));
        }
        iCrmResponse.setResponseHeader(responseHeader);
    }

    /**
     * ACTIVE_ALREADY header mapping.
     *
     * @param iCrmResponse
     * @param extraMessage
     */
    public static final void activeAlreadyMessage(IGlobalResponse iCrmResponse, String extraMessage, boolean extraMessageOnly) {
        responseHeader.setResponseCode(ApplicationConstants.ACTIVE_ALREADY);
        if (extraMessageOnly) {
            responseHeader.setResponseDesc(extraMessage);
        } else {
            responseHeader.setResponseDesc(ApplicationConstants.ACTIVE_ALREADY_MSG + " " + extraMessageValidate(extraMessage));
        }
        iCrmResponse.setResponseHeader(responseHeader);
    }
}
