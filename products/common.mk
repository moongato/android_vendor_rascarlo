# Generic product
PRODUCT_NAME := rascarlo
PRODUCT_BRAND := rascarlo
PRODUCT_DEVICE := generic

PRODUCT_PROPERTY_OVERRIDES += \
    keyguard.no_require_sim=true \
    ro.url.legal=http://www.google.com/intl/%s/mobile/android/basic/phone-legal.html \
    ro.url.legal.android_privacy=http://www.google.com/intl/%s/mobile/android/basic/privacy.html \
    ro.com.google.clientidbase=android-google \
    ro.com.android.wifi-watchlist=GoogleGuest \
    ro.setupwizard.enterprise_mode=1 \
    ro.com.android.dateformat=MM-dd-yyyy \
    ro.com.android.dataroaming=false

# Launcher3 supported devices
ifneq ($(filter rascarlo_hammerhead rascarlo_mako rascarlo_shamu,$(TARGET_PRODUCT)),)
PRODUCT_PACKAGES += \
    Launcher3
# Auto-rotate
PRODUCT_PACKAGE_OVERLAYS += \
    vendor/rascarlo/overlay/Launcher3
endif

# Common overlay
PRODUCT_PACKAGE_OVERLAYS += vendor/rascarlo/overlay/common

# Enable SIP+VoIP on all targets
PRODUCT_COPY_FILES += \
    frameworks/native/data/etc/android.software.sip.voip.xml:system/etc/permissions/android.software.sip.voip.xml

# STK: overlay common to all devices with telephony
ifneq ($(filter rascarlo_hammerhead rascarlo_mako rascarlo_shamu,$(TARGET_PRODUCT)),)
# Build SimToolKit
PRODUCT_PACKAGES += \
    Stk
endif

# Latin IME lib
PRODUCT_COPY_FILES += \
    vendor/rascarlo/proprietary/common/system/lib/libjni_latinime.so:system/lib/libjni_latinime.so

# Backuptool support
PRODUCT_COPY_FILES += \
    vendor/rascarlo/prebuilts/common/system/addon.d/50-rastapop.sh:system/addon.d/50-rastapop.sh \
    vendor/rascarlo/prebuilts/common/system/bin/backuptool.functions:system/bin/backuptool.functions \
    vendor/rascarlo/prebuilts/common/system/bin/backuptool.sh:system/bin/backuptool.sh
