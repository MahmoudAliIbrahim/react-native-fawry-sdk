@objc(FawrySdk)
class FawrySdk: NSObject {

    @objc(initFawry:withResolver:withRejecter:)
    func initFawry(style: String,resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        resolve("Success")
    }
    

    @objc
    func constantsToExport() -> [String: Any]! {
        return [
            "LANGUAGES": [
                "EN": "en_US",
                "AR": "ar_EG"
            ],
            "STYLES": [
                "STYLE_1": "STYLE_1",
                "STYLE_2": "STYLE_2"
            ],
        ]
    }
    @objc
    func requiresMainQueueSetup() -> Bool {
        return true
    }
}
