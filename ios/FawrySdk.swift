@objc(FawrySdk)
class FawrySdk: NSObject {

    @objc(multiply:withB:withResolver:withRejecter:)
    func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        resolve(a*b)
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
}
