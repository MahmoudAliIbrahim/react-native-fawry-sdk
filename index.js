import { NativeModules } from "react-native";

const { FawrySdk } = NativeModules;
export default class FawrySDK {
  static LANGUAGES = {
    AR: FawrySdk.LANGUAGES.AR,
    EN: FawrySdk.LANGUAGES.EN,
  };

  static STYLES = {
    STYLE1: FawrySdk.STYLES.STYLE_1,
    STYLE2: FawrySdk.STYLES.STYLE_2,
  };

  static initSDK(style) {
    return FawrySdk.init(style);
  }

  static initPaymentPlugin(data) {
    return FawrySdk.initPaymentPlugin(
      data.merchantID,
      data.serverURL,
      data.items,
      data.language
    );
  }

  static initCardTokenizer(data) {
    return FawrySdk.initCardTokenizer(
      data.merchantID,
      data.serverURL,
      data.customerMobile,
      data.customerEmail,
      data.language
    );
  }

  static startPaymentActivity() {
    return FawrySdk.startPaymentActivity();
  }

  static resetFawrySDK() {
    FawrySdk.resetFawrySDK();
  }
}
