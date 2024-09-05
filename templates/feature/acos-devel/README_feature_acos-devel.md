---
description: Feature acos-devel
authors: tom.han@autocore.ai
---
	
### Feature acos-devel
	 
Activation of the acos-devel features turns on
features needed for developping and debugging
acos distribution.

This includes:

* adding to images some useful packages
* adding to images the package group 'packagegroup-acos-devel'
* definition of a contionnal the tag 'acos-devel'
    for conditionnal building

  * definition of the distro feature 'acos-devel'
  * adds packages for development in SDK

### How to use acos-devel in conditionnal builds

The following example shows how to activate C/C++ code
specific to acos-devel:

```yocto
CPPFLAGS:append:acos-devel = " -DACOS_DEVEL"
```

Using this, any code enclosed in

```yocto
#ifdef ACOS_DEVEL
...my code specific to acos-devel...
#endif
```

will normaly be effective only if acos-devel is set on.

At this time, it is recommended to use ACOS_DEVEL as tag
within C/C++ code.
