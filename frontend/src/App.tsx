import React, {useEffect, useState} from 'react';
import Checkbox from "./components/Checkbox";
import Input from "./components/Input";
import Button from "./components/Button";
import axios from "axios";
import {Question} from "./domain/questions/Question";
import QuestionCard from "./components/QuestionCard";
import config from "./Config";
import {QuestionType} from "./domain/questions/QuestionType";

function App() {
    const [points, setPoints] = useState<number[]>([]);
    const [questionTypes, setQuestionTypes] = useState<QuestionType[]>([]);
    const [bookmarked, setBookmarked] = useState<boolean>(false);
    const [goodKnow, setGoodKnow] = useState<boolean>(false);
    const [badKnow, setBadKnow] = useState<boolean>(false);
    const [dontKnow, setDontKnow] = useState<boolean>(false);
    const [limit, setLimit] = useState<number|null>(10);
    const [offset, setOffset] = useState<number|null>(0);
    const [questions, setQuestions] = useState<Question[]>([]);
    const [total, setTotal] = useState<number>(0);
    const [readyToFetch, setReadyToFetch] = useState<boolean>(false);

    const fetchParamsFromUrl = () => {
        const search: URLSearchParams = new URLSearchParams(window.location.search);

        const uriBookmarked: boolean = search.get("bookmarked") === 'true';
        if (uriBookmarked !== bookmarked) setBookmarked(uriBookmarked);

        const uriGoodKnow: boolean = search.get("goodKnow") === 'true';
        if (uriGoodKnow !== goodKnow) setGoodKnow(uriGoodKnow);

        const uriBadKnow: boolean = search.get("badKnow") === 'true';
        if (uriBadKnow !== badKnow) setBadKnow(uriBadKnow);

        const uriDontKnow: boolean = search.get("dontKnow") === 'true';
        if (uriDontKnow !== dontKnow) setDontKnow(uriDontKnow);

        const uriOffset: number|null = search.get("offset") ? parseInt(search.get("offset")!) : null;
        if (uriOffset !== offset) setOffset(uriOffset === null || isNaN(uriOffset) ? null : uriOffset);

        const uriLimit: number|null = search.get("limit") ? parseInt(search.get("limit")!) : null;
        if (uriLimit !== limit) setLimit(uriLimit === null || isNaN(uriLimit) ? null : uriLimit);

        const questionTypesUri: string|null = search.get("questionTypes");
        if (questionTypesUri !== null && questionTypesUri.length) {
            const questionTypes: string[] = questionTypesUri.split(',');
            setQuestionTypes(questionTypes.map(questionType => {
                if (questionType === (QuestionType.COMMON + '')) {
                    return QuestionType.COMMON;
                }
                return QuestionType.SPECIAL;
            }));
        } else {
            setQuestionTypes([]);
        }

        const pointsUri: string|null = search.get("points");
        if (pointsUri !== null && pointsUri.length) {
            const points: string[] = pointsUri.split(',');
            setPoints(points.map(point => parseInt(point)));
        } else {
            setPoints([]);
        }
    }

    useEffect(() => {
        fetchParamsFromUrl();
        setTimeout(() => {
            setReadyToFetch(true);
        }, 100)
    }, []);

    useEffect(() => {
        if (readyToFetch) {
            handleOnClickFetchButton();
        }
    }, [readyToFetch]);

    const handleChangeBookmarked = (paramName: string, value: boolean|string) => {
        const search: URLSearchParams = new URLSearchParams(window.location.search);
        search.set(paramName, value + '');
        window.history.replaceState({} , '', window.location.origin + window.location.pathname + "?" + search.toString());
        fetchParamsFromUrl();
    }

    const handleChangePagination = (paramName: string, value: number|string) => {
        const search: URLSearchParams = new URLSearchParams(window.location.search);
        search.set(paramName, (isNaN(parseInt(value + '')) ? '' : parseInt(value + '')) + '');
        window.history.replaceState({} , '', window.location.origin + window.location.pathname + "?" + search.toString());
        fetchParamsFromUrl();
    }

    const handleOnClickFetchButton = async () => {
        const result = await axios.post(config.apiUrl + '/getQuestions', {
            bookmarked,
            goodKnow,
            badKnow,
            dontKnow,
            limit: limit || 10,
            offset: offset || 0,
            questionTypes,
            points
        });
        const questions: Question[] = result.data.list;
        setQuestions(questions || []);
        setTotal(result.data.total || 0);
        window.scroll(0,0);
    }

    const handleChangeQuestionType = (questionType: QuestionType) => {
        const prevState = [...questionTypes];

        const foundIndex = prevState.indexOf(questionType);
        if (foundIndex === -1) {
            prevState.push(questionType);
        } else {
            prevState.splice(foundIndex, 1);
        }

        const search: URLSearchParams = new URLSearchParams(window.location.search);
        search.set('questionTypes', prevState.join(','));
        window.history.replaceState({} , '', window.location.origin + window.location.pathname + "?" + search.toString());
        fetchParamsFromUrl();
    }

    const handleChangePoint = (point: number) => {
        const prevState = [...points];

        const foundIndex = prevState.indexOf(point);
        if (foundIndex === -1) {
            prevState.push(point);
        } else {
            prevState.splice(foundIndex, 1);
        }

        const search: URLSearchParams = new URLSearchParams(window.location.search);
        search.set('points', prevState.join(','));
        window.history.replaceState({} , '', window.location.origin + window.location.pathname + "?" + search.toString());
        fetchParamsFromUrl();
    }

    return (
        <div>
            <div style={{display: 'flex', alignItems: 'flex-end', flexWrap: 'wrap', padding: '10px'}}>
                <Checkbox label={"Good know"} style={{margin: '10px'}} enabled={goodKnow} onChange={(value: boolean) => handleChangeBookmarked('goodKnow', value)} />
                <Checkbox label={"Bad know"} style={{margin: '10px'}} enabled={badKnow} onChange={(value: boolean) => handleChangeBookmarked('badKnow', value)} />
                <Checkbox label={"Dont know"} style={{margin: '10px'}} enabled={dontKnow} onChange={(value: boolean) => handleChangeBookmarked('dontKnow', value)} />

                <Checkbox label={"Special"} style={{margin: '10px'}} enabled={questionTypes.indexOf(QuestionType.SPECIAL) > -1} onChange={(value: boolean) => handleChangeQuestionType(QuestionType.SPECIAL)} />
                <Checkbox label={"Common"} style={{margin: '10px'}} enabled={questionTypes.indexOf(QuestionType.COMMON) > -1} onChange={(value: boolean) => handleChangeQuestionType(QuestionType.COMMON)} />

                <Checkbox label={"1 Point"} style={{margin: '10px'}} enabled={points.indexOf(1) > -1} onChange={(value: boolean) => handleChangePoint(1)} />
                <Checkbox label={"2 Points"} style={{margin: '10px'}} enabled={points.indexOf(2) > -1} onChange={(value: boolean) => handleChangePoint(2)} />
                <Checkbox label={"3 Points"} style={{margin: '10px'}} enabled={points.indexOf(3) > -1} onChange={(value: boolean) => handleChangePoint(3)} />

                <Checkbox label={"Bookmarked"} style={{margin: '10px'}} enabled={bookmarked} onChange={(value: boolean) => handleChangeBookmarked('bookmarked', value)} />
                <Input label={'Limit'} style={{width: '55px', margin: '10px'}} value={limit} onChange={(value: string) => handleChangePagination('limit', value)} />
                <Input label={'Offset'} style={{width: '55px', margin: '10px'}} value={offset} onChange={(value: string) => handleChangePagination('offset', value)} />

                <Button label={'Fetch'} style={{margin: '10px'}} onClick={handleOnClickFetchButton} />

                <div style={{margin: '10px'}}>Total: {total}</div>
            </div>

            {questions.map((question, index) => (
                <QuestionCard question={question} key={question.id + '-' + index} style={{marginBottom: '15px', marginLeft: '15px', marginRight: '15px'}}/>
            ))}

            <div>
                <Button label={'Fetch'} style={{margin: '10px'}} onClick={handleOnClickFetchButton} />
            </div>
        </div>
    );
}

export default App;
