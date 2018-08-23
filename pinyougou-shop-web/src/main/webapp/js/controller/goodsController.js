/** 定义控制器层 */
app.controller('goodsController', function($scope, $controller, baseService,$location,uploadService){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});

    /** 查询全部 */
    $scope.findAll = function(){
        baseService.sendGet("/goods/findAll").then(function(response){
            $scope.dataList = response.data;
        });
    };

    /** 定义搜索对象 */
    $scope.searchEntity = {};
    /** 分页查询 */
    $scope.search = function(page, rows){
        baseService.findByPage("/goods/findByPage", page, 
			rows, $scope.searchEntity)
            .then(function(response){
                $scope.dataList = response.data.rows;
                /** 更新总记录数 */
                $scope.paginationConf.totalItems = response.data.total;
            });
    };

    /** 添加或修改 */
    $scope.saveOrUpdate = function(){
        var url = "save";
        if ($scope.entity.id){
            url = "update";
        }
        /** 发送post请求 */
        baseService.sendPost("/goods/" + url, $scope.entity)
            .then(function(response){
                if (response.data){
                    /** 重新加载数据 */
                    $scope.reload();
                }else{
                    alert("操作失败！");
                }
            });
    };

    /** 显示修改 */
    $scope.show = function(entity){
        $scope.entity = JSON.parse(JSON.stringify(entity));
    };

    /** 批量删除 */
    $scope.delete = function(){
        if ($scope.ids.length > 0){
            baseService.deleteById("/goods/delete", $scope.ids)
                .then(function(response){
                    if (response.data){
                        $scope.reload();
                    }else{
                        alert("删除失败！");
                    }
                });
        }
    };

    $scope.$watch('goods.category1Id',function (newValue,oldValue) {
        if(newValue){
            $scope.findItemCatByParentId(newValue,"itemCatList2");
        }else {
            $scope.itemCatList2=[];
        }
    });
    $scope.$watch('goods.category2Id', function(newValue, oldValue){
        if (newValue){
            /** 根据选择的值查询三级分类 */
            $scope.findItemCatByParentId(newValue, "itemCatList3");
        }else{
            $scope.itemCatList3 = [];
        }
    });
    $scope.$watch('goods.category3Id',function (newValue,oldValue) {
        if(!newValue){
            return;
        }
        for(var i=0;i<$scope.itemCatList3.length;i++){
            var itemCat=$scope.itemCatList3[i];
            if(itemCat.id==newValue){
                $scope.goods.typeTemplateId=itemCat.typeId;
                break;
            }
        }
    });
    $scope.$watch('goods.typeTemplateId',function (newValue,oldValue) {
       if(!newValue){
            return;
       }
       baseService.findOne("/typeTemplate/findOne",newValue)
           .then(function (response) {
               $scope.brandId=JSON.parse(response.data.brandIds);
               $scope.goods.goodsDesc.customAttributeItems =
                   JSON.parse(response.data.customAttributeItems);
           });
       baseService.findOne("/typeTemplate/findSpecByTemplateId",newValue).then(function (response) {
           $scope.specList=response.data;

       });
    });


    $scope.findItemCatByParentId = function(parentId, name){

        // 发送异步请求
        baseService.sendGet("/itemCat/findItemCatByParentId",
            "parentId=" + parentId).then(function(response){

            // 第一个下拉列表中的数据
            $scope[name] = response.data;
        },function () {
            alert("查询失败");
        });
    };

    $scope.searchEntity={};
    $scope.search=function (page,rows) {
        baseService.findByPage("/goods/search",page,rows,$scope.searchEntity)
            .then(function (response) {
                $scope.dataList = response.data.rows;
                $scope.paginationConf.totalItems = response.data.total;
            });
    };

    $scope.findOne=function () {
        var id = $location.search().id;
        if(id==null){
            return;
        }
        baseService.findOne("/goods/findOne",id)
            .then(function (response) {
                $scope.brandIds = JSON.parse(response.data.brandIds);
            });
    };
    $scope.status=['未审核','已审核','审核未通过','关闭'];


    $scope.goods={'goodDesc':{'itemImages':[],'specificationItems':[]}};
    $scope.updateSpecAttr=function ($event,name,value) {
        var obj=$scope.searchJsonByKey($scope.goods.goodDesc.specificationItems,'attributeName',name);

    }

});