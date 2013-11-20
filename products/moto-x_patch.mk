# Moto-x patch

LOCAL_PATH:= vendor/rascarlo/proprietary/system

# Bin
PRODUCT_COPY_FILES += \
        $(LOCAL_PATH)/bin/dalvikvm:system/bin/dalvikvm \
        $(LOCAL_PATH)/bin/dexopt:system/bin/dexopt

# Lib
PRODUCT_COPY_FILES += \
        $(LOCAL_PATH)/lib/libc.so:system/lib/libc.so \
        $(LOCAL_PATH)/lib/libcutils.so:system/lib/libcutils.so \
        $(LOCAL_PATH)/lib/libdvm.so:system/lib/libdvm.so \
        $(LOCAL_PATH)/lib/libm.so:system/lib/libm.so \
        $(LOCAL_PATH)/lib/libqc-opt.so:system/lib/libqc-opt.so
