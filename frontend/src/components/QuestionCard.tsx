import React, {useState} from 'react';
import {Question} from "../domain/questions/Question";
import Button from "./Button";
import QuestionMediaBlock from "./QuestionMediaBlock";
import Checkbox from "./Checkbox";
import axios from "axios";
import config from "../Config";
import LanguageSwitch from "./LanguageSwitch";
import {Language} from "../domain/questions/Language";

interface Props {
    question: Question,
    style?: React.CSSProperties
}

export default function QuestionCard(props: Props) {

    const [language, setLanguage] = useState<Language>(Language.PL);
    const [answerIsRight, setAnswerIsRight] = useState<boolean | null>(null);
    const [isNotesShow, setIsNotesShow] = useState<boolean>(false);
    const [knowType, setKnowType] = useState<'GOOD_KNOW'|'MAYBE'|'DONT_KNOW'|string>(props.question.knowType);
    const [isBookmarked, setIsBookmarked] = useState<boolean>(props.question.bookmarked);

    const ifTrueFalseQuestion = props.question.rightAnswer === 'T' || props.question.rightAnswer === 'N';

    const handleClickAnswer = (value: 'T' | 'N' | 'A' | 'B' | 'C') => {
        if (answerIsRight !== null) return;
        setAnswerIsRight(props.question.rightAnswer === value);
    }

    const getButtonResultStyle = (answer: string): React.CSSProperties => {
        if (answerIsRight === null) return {};
        if (answerIsRight) {
            if (props.question.rightAnswer === answer) {
                return {backgroundColor: 'rgb(27 199 27)'};
            }
            return {}
        } else {
            if (props.question.rightAnswer !== answer) {
                return {backgroundColor: 'red'};
            }
            return {backgroundColor: 'rgb(27 199 27)'};
        }
    }

    const handleChangeKnowType = async (value: 'GOOD_KNOW'|'MAYBE'|'DONT_KNOW') => {
        setKnowType(value);
        const result = await axios.post(config.apiUrl + '/setKnowType/'+props.question.id+'/'+value);
    }

    const handleChangeBookmarked = async (value: boolean) => {
        setIsBookmarked(value);
        const result = await axios.post(config.apiUrl + '/setBookmarked/'+props.question.id+'/'+value);
    }

    return (
        <div style={{...styles.wrapper, ...(props.style || {})}}>
            <QuestionMediaBlock media={props.question.media} style={{marginBottom: '15px'}}/>

            <div style={styles.text}>
                {language === Language.PL ? props.question.questionTextPL : props.question.questionTextENG}
            </div>

            <div style={styles.answersWrapper}>
                {ifTrueFalseQuestion && (
                    <div style={{textAlign: 'center' as const}}>
                        <Button onClick={() => handleClickAnswer('T')} label={language === Language.PL ? 'Tak' : 'Yes'}
                                style={{padding: '10px 16px', marginRight: '10px', ...getButtonResultStyle('T')}}
                        />

                        <Button onClick={() => handleClickAnswer('N')} label={language === Language.PL ? 'Nie' : 'No'}
                                style={{padding: '10px 16px', ...getButtonResultStyle('N')}}
                        />
                    </div>
                )}

                {!ifTrueFalseQuestion && (
                    <>
                        <div style={{display: 'flex', alignItems: 'center', marginBottom: '10px'}}>
                            <Button label={'A'}
                                    onClick={() => handleClickAnswer('A')}
                                    style={{padding: '6px 10px', marginRight: '10px', ...getButtonResultStyle('A')}}
                            />
                            <div style={{fontSize: '18px'}}>
                                {language === Language.PL ? props.question.answerATextPL : props.question.answerATextENG}
                            </div>
                        </div>

                        <div style={{display: 'flex', alignItems: 'center', marginBottom: '10px'}}>
                            <Button label={'B'}
                                    onClick={() => handleClickAnswer('B')}
                                    style={{padding: '6px 10px', marginRight: '10px', ...getButtonResultStyle('B')}}
                            />
                            <div style={{fontSize: '18px'}}>
                                {language === Language.PL ? props.question.answerBTextPL : props.question.answerBTextENG}
                            </div>
                        </div>

                        <div style={{display: 'flex', alignItems: 'center'}}>
                            <Button label={'C'}
                                    onClick={() => handleClickAnswer('C')}
                                    style={{padding: '6px 10px', marginRight: '10px', ...getButtonResultStyle('C')}}
                            />
                            <div style={{fontSize: '18px'}}>
                                {language === Language.PL ? props.question.answerCTextPL : props.question.answerCTextENG}
                            </div>
                        </div>
                    </>
                )}
            </div>

            <div style={{marginTop: '15px', display: 'flex', alignItems: 'center', flexWrap: 'wrap'}}>
                <Checkbox label={"Good know"} enabled={knowType === 'GOOD_KNOW'}
                          onChange={(value: boolean) => handleChangeKnowType('GOOD_KNOW')}
                          style={{margin: '10px'}}
                />

                <Checkbox label={"Bad know"} enabled={knowType === 'MAYBE'}
                          onChange={(value: boolean) => handleChangeKnowType('MAYBE')}
                          style={{margin: '10px'}}
                />

                <Checkbox label={"Dont know"} enabled={knowType === 'DONT_KNOW'}
                          onChange={(value: boolean) => handleChangeKnowType('DONT_KNOW')}
                          style={{margin: '10px'}}
                />

                <div style={{width: '100%'}} />

                <Checkbox label={"Bookmarked"} enabled={isBookmarked}
                          onChange={(value: boolean) => handleChangeBookmarked(value)}
                          style={{margin: '10px'}}
                />

                <LanguageSwitch activeLanguage={language} onChange={(language: Language) => setLanguage(language)}/>

                <Button label={'Show description'} onClick={() => setIsNotesShow((prevState) => !prevState)}/>
            </div>

            {isNotesShow && (
                <div>
                    <p><b>Prawa: </b><a target="_blank" href={'https://www.google.com/search?q='+encodeURIComponent(props.question.law)}>{props.question.law}</a></p>
                    <p><b>Temat: </b>{props.question.lawDescription}</p>
                    <p><b>Questions of security: </b>{props.question.securityThemOfQuestion}</p>
                    <p><b>Points: </b>{props.question.points}</p>
                    <p><b>Block: </b>{props.question.blockName}</p>
                    <p><b>Question name: </b>{props.question.name}</p>
                    <p><b>Question number: </b>{props.question.number}</p>
                </div>
            )}
        </div>
    );
}

const styles = {
    wrapper: {
        padding: '20px',
        borderRadius: '4px',
        backgroundColor: 'rgb(250,249,207)'
    },

    text: {
        fontSize: '18px',
        textAlign: 'center' as const,
        marginBottom: '15px'
    },

    answersWrapper: {},

}
