/** 定义控制器层 */
app.controller('itemCatController', function($scope, $controller, baseService){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});

    /** 查询全部 */
    $scope.findAll = function(){
        baseService.sendGet("/itemCat/findAll").then(function(response){
            $scope.dataList = response.data;
        });
    };

    /** 定义搜索对象 */
    $scope.searchEntity = {};
    /** 分页查询 */
    $scope.search = function(page, rows){
        baseService.findByPage("/itemCat/findByPage", page, 
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
        }else {
            $scope.itemCat.parentId=$scope.parentId;
        }
        /** 发送post请求 */
        baseService.sendPost("/itemCat/" + url, $scope.entity)
            .then(function(response){
                if (response.data){
                    /** 重新加载数据 */
                    //$scope.reload();
                    $scope.findItemCatByParentId($scope.parentId);
                    $scope.itemCat=null;
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
            baseService.deleteById("/itemCat/delete", $scope.ids)
                .then(function(response){
                    if (response.data){
                        $scope.reload();
                    }else{
                        alert("删除失败！");
                    }
                });
        }
    };
    $scope.parentId=0;
    $scope.findItemCatByParentId=function (parentId)  {
        $scope.parentId=parentId;
        baseService.sendGet("/itemCat/findItemCatByParentId","parentId="+parentId)
            .then(function (response) {
            $scope.dataList=response.data;
        })
    }

    $scope.grade=1;
    $scope.selectList=function (entity,grade) {
            $scope.grade=grade;
            if(grade==1){
                $scope.itemCat_1=null;
                $scope.itemCat_2=null;
            }
            if(grade==2){
                    $scope.itemCat_1=entity;
                    $scope.itemCat_2=null;
            }
            if(grade==3){
                $scope.itemCat_2==entity;
            }
            $scope.findItemCatByParentId(entity.id);
    }

    $scope.findTypeTemplateList=function () {
        baseService.sendGet("/typeTemplate/findTypeTemplateList")
            .then(function (response) {
                $scope.typeTemplateList=response.data;
            })
    };





});