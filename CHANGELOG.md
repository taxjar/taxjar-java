# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [5.0.1] - 2021-10-18
- Fix API error response handling without `error`, `detail`, or `status` keys

## [5.0.0] - 2021-05-13
- Support custom headers and API version via `x-api-version`
- Change return type of `getApiConfig` method from `String` to `Object`  
- Update junit dependency to version `4.13.1`

## [4.0.0] - 2020-04-30
- Update retrofit dependency to version `2.8.1`; note: this change drops support for TLS 1.1 and TLS 1.0

## [3.3.0] - 2020-03-31
- Add information to custom user agent for debugging and informational purposes
- Fix issue with retrofit dependency not encoding path parameters properly

## [3.2.0] - 2019-07-08
- Support `exemption_type` param for order-level exemptions

## [3.1.0] - 2019-07-03
- Support `provider` param for marketplace exempt transactions
- Fix multi-threading issue by changing `apiService` to instance variable [#19](https://github.com/taxjar/taxjar-java/pull/19)

## [3.0.0] - 2019-02-15
- Make sure config params are thread-safe and not shared across multiple clients

## [2.0.0] - 2019-01-04
- Support custom timeout
- Fix response attributes in `breakdown` returned from `taxForOrder`

## [1.3.0] - 2018-10-19
- Support address validation for Plus customers via `validateAddress` method

## [1.2.0] - 2018-09-18
- Add `jurisdictions` object to `taxForOrder` response

## [1.1.0] - 2018-05-07
- Support customer exemptions
- Allow setting `apiUrl` to support sandbox environment

## [1.0.1] - 2017-09-22
- Support `product_tax_code` attribute in line item entity

## [1.0.0] - 2017-07-12
- Initial release

[Unreleased]: https://github.com/taxjar/taxjar-java/compare/v5.0.1...HEAD
[5.0.1]: https://github.com/taxjar/taxjar-java/compare/v5.0.0...v5.0.1
[5.0.0]: https://github.com/taxjar/taxjar-java/compare/v4.0.0...v5.0.0
[4.0.0]: https://github.com/taxjar/taxjar-java/compare/v3.3.0...v4.0.0
[3.3.0]: https://github.com/taxjar/taxjar-java/compare/v3.2.0...v3.3.0
[3.2.0]: https://github.com/taxjar/taxjar-java/compare/v3.1.0...v3.2.0
[3.1.0]: https://github.com/taxjar/taxjar-java/compare/v3.0.0...v3.1.0
[3.0.0]: https://github.com/taxjar/taxjar-java/compare/v2.0.0...v3.0.0
[2.0.0]: https://github.com/taxjar/taxjar-java/compare/v1.3.0...v2.0.0
[1.3.0]: https://github.com/taxjar/taxjar-java/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/taxjar/taxjar-java/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/taxjar/taxjar-java/compare/v1.0.1...v1.1.0
[1.0.1]: https://github.com/taxjar/taxjar-java/compare/v1.0.0...v1.0.1
